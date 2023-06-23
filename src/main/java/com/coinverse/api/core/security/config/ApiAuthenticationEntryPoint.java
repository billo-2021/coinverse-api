package com.coinverse.api.core.security.config;

import com.coinverse.api.common.errors.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final AuthenticationEntryPointErrorProvider authErrorProvider;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        final ErrorResponse errorResponse = authErrorProvider.getErrorResponse();

        if (errorResponse.getHttpStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        } else if(errorResponse.getHttpStatusCode() == HttpStatus.FORBIDDEN.value()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        authErrorProvider.resetError();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        sendErrorResponse(response, errorResponse.toJson());
    }

    private void sendErrorResponse(final HttpServletResponse response, final String payload) throws IOException {
        final PrintWriter responseWriter = response.getWriter();

        responseWriter.print(payload);
        responseWriter.flush();
    }
}
