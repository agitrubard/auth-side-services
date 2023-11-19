package com.agitrubard.authside.common.adapter.in.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * A generic response class used to encapsulate the response data for API requests.
 *
 * @param <T> The type of data contained in the response.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Builder
public class AuthSideResponse<T> {

    /**
     * The timestamp when the response is generated, with a default value of the current time.
     */
    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    /**
     * The HTTP status of the response.
     */
    private HttpStatus httpStatus;

    /**
     * A flag indicating whether the request was successful.
     */
    private Boolean isSuccess;

    /**
     * The response data of the specified generic type.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;


    /**
     * A pre-built success response with HTTP status 200 (OK) and a success flag set to true.
     */
    public static final AuthSideResponse<Void> SUCCESS = AuthSideResponse.<Void>builder()
            .httpStatus(HttpStatus.OK)
            .isSuccess(true).build();

    /**
     * Creates a new success response with the specified response data.
     *
     * @param response The data to be included in the response.
     * @param <T>      The type of the response data.
     * @return A success response with the provided data and HTTP status 200 (OK).
     */
    public static <T> AuthSideResponse<T> successOf(final T response) {
        return AuthSideResponse.<T>builder()
                .httpStatus(HttpStatus.OK)
                .isSuccess(true)
                .response(response).build();
    }

}
