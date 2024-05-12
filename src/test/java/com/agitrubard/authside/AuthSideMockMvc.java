package com.agitrubard.authside;

import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import com.agitrubard.authside.util.AuthSideMockResultMatchersBuilders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@Component
@RequiredArgsConstructor
public class AuthSideMockMvc {

    private final MockMvc mockMvc;

    public ResultActions perform(final MockHttpServletRequestBuilder mockHttpServletRequestBuilder,
                                 final AuthSideResponse<?> mockResponse) throws Exception {

        return mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(AuthSideMockResultMatchersBuilders.time()
                        .isNotEmpty())
                .andExpect(AuthSideMockResultMatchersBuilders.httpStatus()
                        .isString())
                .andExpect(AuthSideMockResultMatchersBuilders.httpStatus()
                        .value(mockResponse.getHttpStatus().getReasonPhrase()))
                .andExpect(AuthSideMockResultMatchersBuilders.isSuccess()
                        .isBoolean())
                .andExpect(AuthSideMockResultMatchersBuilders.isSuccess()
                        .value(mockResponse.getIsSuccess()));
    }

    public ResultActions perform(final MockHttpServletRequestBuilder mockHttpServletRequestBuilder,
                                 final AuthSideErrorResponse mockErrorResponse) throws Exception {

        return mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(AuthSideMockResultMatchersBuilders.time()
                        .isNotEmpty())
                .andExpect(AuthSideMockResultMatchersBuilders.httpStatus()
                        .isString())
                .andExpect(AuthSideMockResultMatchersBuilders.httpStatus()
                        .value(mockErrorResponse.getHttpStatus().name()))
                .andExpect(AuthSideMockResultMatchersBuilders.isSuccess()
                        .isBoolean())
                .andExpect(AuthSideMockResultMatchersBuilders.header()
                        .isString())
                .andExpect(AuthSideMockResultMatchersBuilders.header()
                        .value(mockErrorResponse.getHeader()))
                .andExpect(AuthSideMockResultMatchersBuilders.content()
                        .doesNotExist());
    }

}
