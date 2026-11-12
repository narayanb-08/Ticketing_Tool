package com.sumpositive.ticketing.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
		private final JwtSecurity jwtSecurity;
		
		public JwtAuthenticationFilter(JwtSecurity jwtSecurity) {
			this.jwtSecurity = jwtSecurity;
		}
		
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
			String authorizationHeader = request.getHeader("Authorization");
			
			if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String token = authorizationHeader.substring(7);
			
			try {
				String email=jwtSecurity.extractEmail(token);
				
				if(email != null && jwtSecurity.validateToken(token,email)   &&   SecurityContextHolder.getContext().getAuthentication() == null) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,null,Collections.emptyList());
					
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (Exception e) {
				SecurityContextHolder.clearContext();
			}
			
			filterChain.doFilter(request, response);
		}
}
