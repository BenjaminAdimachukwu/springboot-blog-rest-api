package com.springboot.blog.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.payload.ErrorDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/*@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Access Denied: " + accessDeniedException.getMessage(),
                request.getRequestURI()
        );
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        //response.getWriter().write(new ObjectMapper().writeValueAsString(errorDetails));
        response.getWriter().write("{\"message\": \"Custom Access Denied: You don’t have permission to access this resource.\"}");
    }
}*/

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("CustomAccessDeniedHandler called");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"Custom Access Denied: You don’t have permission to access this resource.\"}");
    }
}
