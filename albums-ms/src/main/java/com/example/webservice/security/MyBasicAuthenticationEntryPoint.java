package com.example.webservice.security;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    public void commence(
           HttpServletRequest request, HttpServletResponse response, AuthenticationException
            authEx)
                throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"\" + getRealmName()+ \"\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 -" + authEx.getMessage());
    }

    public void afterPropertiesSet() {
        setRealmName("DarenAPI");
        super.afterPropertiesSet();
    }
}
