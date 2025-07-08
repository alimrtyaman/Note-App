package com.aliyaman.noteapp.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {



    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String header;
        String token;
        String userName;

        header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")){
             filterChain.doFilter(request, response);
             log.warn("sikinti baba");
             return;
        }

        token = header.substring(7);

        try{
            userName = jwtService.getUserName(token);
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                if (userDetails != null && !jwtService.isTokenExpired(token)){
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());

                    auth.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch (ExpiredJwtException ex){
            log.warn("Token is expired  " +  ex.getMessage());
        }catch (Exception ex){
            log.warn(ex.getMessage());
        }
        filterChain.doFilter(request , response);
    }
}
