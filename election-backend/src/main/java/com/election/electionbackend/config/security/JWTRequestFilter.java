package com.election.electionbackend.config.security;

import com.election.electionbackend.APIConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Enumeration;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @Autowired
    APIConfig apiConfig;

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    }

//    private void addAccessControlHeaders(HttpServletRequest request,
//                                         HttpServletResponse response) {
//        // add the access-control headers
//        String originHeader = request.getHeader(HttpHeaders.ORIGIN);
//        if (originHeader == null) {
//            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        } else {
//            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, originHeader);
//        }
//
//        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//
//        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE,OPTIONS");
//        //response.addHeader("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, X-Forwarded-For, Content-Type, Accept");
//        //response.addHeader("Access-Control-Expose-Headers", "Authorization, Content-Type");
//        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
//                HttpHeaders.AUTHORIZATION + ", " + HttpHeaders.CONTENT_TYPE + ", " + APIConfig.IP_FORWARDED_FOR);
//        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
//                HttpHeaders.AUTHORIZATION + ", " + HttpHeaders.CONTENT_TYPE + ", " + APIConfig.IP_FORWARDED_FOR);
//    }
//
//    private void logRequestContextAndCookies(HttpServletRequest request) {
//        System.out.println("Request Method=" + request.getMethod());
//        System.out.println("Request ServerName=" + request.getServerName());
//        System.out.println("Request ServletPath=" + request.getServletPath());
//        System.out.println("Request ContextPath=" + request.getContextPath());
//        System.out.println("Request PathTranslated=" + request.getPathTranslated());
//        System.out.println("Request PathInfo=" + request.getPathInfo());
//        System.out.println("Request RequestURI=" + request.getRequestURI());
//        System.out.println("Request QueryString=" + request.getQueryString());
//        System.out.println("Request RemoteUser=" + request.getRemoteUser());
//
//        // show headers
//        Enumeration<String> headerNames = request.getHeaderNames();
//        System.out.println("Requestheaders:");
//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                String nextHeaderName = headerNames.nextElement();
//                System.out.println("Header['" + nextHeaderName + "'] = " + request.getHeader(nextHeaderName));
//            }
//        }
//
//        // show cookies
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                String cookieName = cookie.getName();
//                String cookieValue = cookie.getValue();
//                System.out.println("Cookie['" + cookieName + "']: " + cookieValue);
//            }
//        }
//    }
}
