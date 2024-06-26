package com.agitrubard.authside;

import com.agitrubard.authside.auth.application.config.AuthSideTokenConfigurationParameter;
import com.agitrubard.authside.auth.application.port.out.AuthSideLoginAttemptReadPort;
import com.agitrubard.authside.auth.application.port.out.AuthSideUserReadPort;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUser;
import com.agitrubard.authside.util.AuthSideValidTestData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public abstract class AuthSideRestControllerTest implements AuthSideTestcontainer {

    @Autowired
    protected AuthSideMockMvc authSideMockMvc;


    protected AuthSideToken adminUserToken;
    protected AuthSideToken userToken;


    @Autowired
    private AuthSideTokenConfigurationParameter tokenConfigurationParameter;

    @Autowired
    private AuthSideUserReadPort userReadPort;

    @Autowired
    private AuthSideLoginAttemptReadPort loginAttemptReadPort;

    @BeforeEach
    @SuppressWarnings("OptionalGetWithoutIsPresent disabled because of the test data is valid")
    void init() {
        AuthSideUser adminUser = userReadPort.findById(AuthSideValidTestData.AdminUser.ID).get();
        AuthSideLoginAttempt loginAttemptOfAdminUser = loginAttemptReadPort.findByUserId(adminUser.getId());
        this.adminUserToken = this.generate(adminUser.getPayload(loginAttemptOfAdminUser));

        AuthSideUser user = userReadPort.findById(AuthSideValidTestData.ReadUser.ID).get();
        AuthSideLoginAttempt loginAttemptOfUser = loginAttemptReadPort.findByUserId(user.getId());
        this.userToken = this.generate(user.getPayload(loginAttemptOfUser));
    }

    protected AuthSideToken generate(Claims claims) {
        final long currentTimeMillis = System.currentTimeMillis();

        final Date tokenIssuedAt = new Date(currentTimeMillis);
        final JwtBuilder tokenBuilder = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .issuer(tokenConfigurationParameter.getIssuer())
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .issuedAt(tokenIssuedAt);

        final Date accessTokenExpiresAt = DateUtils.addMinutes(
                new Date(currentTimeMillis),
                tokenConfigurationParameter.getAccessTokenExpireMinute()
        );
        final String accessToken = tokenBuilder
                .id(UUID.randomUUID().toString())
                .expiration(accessTokenExpiresAt)
                .claims(claims)
                .compact();

        final Date refreshTokenExpiresAt = DateUtils.addDays(
                new Date(currentTimeMillis),
                tokenConfigurationParameter.getRefreshTokenExpireDay()
        );
        final String refreshToken = tokenBuilder
                .id(UUID.randomUUID().toString())
                .expiration(refreshTokenExpiresAt)
                .claim(AuthSideTokenClaim.USER_ID.getValue(), claims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .compact();

        return AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();
    }

}
