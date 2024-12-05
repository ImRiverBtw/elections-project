package com.election.electionbackend.security;

import com.election.electionbackend.APIConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JWTRequestFilter extends OncePerRequestFilter {


    private APIConfig apiConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //get the encrypted token string from the request header
        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        //block the request if no token was found
        if (encryptedToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "no token provided.");
            return;
        }
        try {
            //decode the token after removing Bearer prefix
            JWToken jwToken = JWToken.decode(encryptedToken.replace("Bearer ", ""),
                    this.apiConfig.getPassphrase(), this.apiConfig.getIssuer());
            //pass-on token info as request attribute
            request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);
            //proceed along the chain of filters towards the REST controller.\
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    e.getMessage() + "You need to log in first.");
            return;
        }
    }
}
