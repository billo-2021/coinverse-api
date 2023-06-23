package com.coinverse.api.core.security.config;

import com.coinverse.api.common.security.exceptions.ResourceAccessDeniedException;
import com.coinverse.api.common.errors.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        sendErrorResponse(response);
    }

    private void sendErrorResponse(final HttpServletResponse response) throws IOException {
        final ResourceAccessDeniedException resourceAccessDeniedException = new ResourceAccessDeniedException();
        final ErrorResponse errorResponse = new ErrorResponse(resourceAccessDeniedException);
        final String jsonResponse = errorResponse.toJson();

        final PrintWriter responseWriter = response.getWriter();
        responseWriter.print(jsonResponse);
        responseWriter.flush();
    }
}
