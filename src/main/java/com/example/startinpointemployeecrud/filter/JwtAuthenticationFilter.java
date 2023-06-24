package com.example.startinpointemployeecrud.filter;

import com.example.startinpointemployeecrud.model.User;
import com.example.startinpointemployeecrud.service.CustomUserDetailService;
import com.example.startinpointemployeecrud.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailService service;

    private  Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            System.out.println("Request :: " + request );

            String jwt = getJwtFromRequest(request);

            System.out.println("JWT :: " + jwt);

            if (!StringUtils.hasText(jwt)){
               UserDetails userDetails =  service.loadUserByUsername(new User().getUsername());
               jwt = jwtUtil.generateToken(userDetails);
               response.addHeader("Authorization", "Bearer " +jwt);
            }

            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {

                String username = jwtUtil.extractUsername(jwt);

                UserDetails userDetails = service.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
