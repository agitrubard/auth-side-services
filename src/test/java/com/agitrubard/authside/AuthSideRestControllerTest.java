package com.agitrubard.authside;

import com.agitrubard.authside.auth.application.config.AuthSideTokenConfigurationParameter;
import com.agitrubard.authside.auth.application.port.out.AuthSideLoginAttemptReadPort;
import com.agitrubard.authside.auth.application.port.out.AuthSideUserReadPort;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUser;
import com.agitrubard.authside.util.AuthSideValidTestData;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public abstract class AuthSideRestControllerTest implements AuthSideTestcontainer {

    @Autowired
    protected MockMvc mockMvc;


    protected AuthSideToken adminUserToken;
    protected AuthSideToken userToken;


    @Autowired
    private AuthSideTokenConfigurationParameter tokenConfigurationParameter;

    @Autowired
    private AuthSideUserReadPort userReadPort;

    @Autowired
    private AuthSideLoginAttemptReadPort loginAttemptReadPort;

    @BeforeEach
    void init() {
        AuthSideUser adminUser = userReadPort.findById(AuthSideValidTestData.AdminUser.ID).get();
        AuthSideLoginAttempt loginAttemptOfAdminUser = loginAttemptReadPort
                .findByUserId(adminUser.getId());
        this.userToken = this.generate(adminUser.getClaims(loginAttemptOfAdminUser));

        AuthSideUser user = userReadPort.findById(AuthSideValidTestData.ReadUser.ID).get();
        AuthSideLoginAttempt loginAttemptOfUser = loginAttemptReadPort
                .findByUserId(user.getId());
        this.adminUserToken = this.generate(user.getClaims(loginAttemptOfUser));
    }

    protected AuthSideToken generate(Map<String, Object> claims) {
        final long currentTimeMillis = System.currentTimeMillis();

        final Date tokenIssuedAt = new Date(currentTimeMillis);

        final Date accessTokenExpiresAt = DateUtils.addMinutes(new Date(currentTimeMillis), tokenConfigurationParameter.getAccessTokenExpireMinute());
        final String accessToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(tokenConfigurationParameter.getIssuer())
                .issuedAt(tokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .claims(claims)
                .compact();

        final Date refreshTokenExpiresAt = DateUtils.addDays(new Date(currentTimeMillis), tokenConfigurationParameter.getRefreshTokenExpireDay());
        final JwtBuilder refreshTokenBuilder = Jwts.builder();
        final String refreshToken = refreshTokenBuilder
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(tokenConfigurationParameter.getIssuer())
                .issuedAt(tokenIssuedAt)
                .expiration(refreshTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .claim(AuthSideTokenClaim.USER_ID.getValue(), claims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .compact();

        return AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();
    }

}
