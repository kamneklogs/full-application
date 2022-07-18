package com.kamneklogs.fullapp.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JWTEntryPoint implements AuthenticationEntryPoint {// Verify if there is a valid token otherwise return 401
                                                                // error

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        log.error( authException.getMessage());

        log.error( request.getRequestURI());



        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

}
