package com.sumpositive.ticketing.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sumpositive.ticketing.user.entity.User;
import com.sumpositive.ticketing.user.enums.AccountStatus;
import com.sumpositive.ticketing.user.repository.UserRepository;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
		private final JwtSecurity jwtSecurity;
		private final UserRepository userRepository;
		
		public JwtAuthenticationFilter(JwtSecurity jwtSecurity, UserRepository userRepository) {
			this.jwtSecurity = jwtSecurity;
			this.userRepository = userRepository;
		}
		
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
			String authorizationHeader = request.getHeader("Authorization");
			
			if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String token = authorizationHeader.substring(7).trim();
			
			if(token.isEmpty()) {
				filterChain.doFilter(request, response);
				return;
			}
			
			try {
				String email=jwtSecurity.extractEmail(token);
				
				if(email != null && jwtSecurity.validateToken(token,email)   &&   SecurityContextHolder.getContext().getAuthentication() == null) {
					
					User user = userRepository.findByEmail(email).orElse(null);
					if (user != null
					        && user.isActive()
					        && user.getUserRole() != null
					        && (user.getAccountStatus() == AccountStatus.PENDING
					            || user.getAccountStatus() == AccountStatus.APPROVED)) {
						SimpleGrantedAuthority authority =
						        new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name());					
					
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),null,List.of(authority));
					
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContext context = SecurityContextHolder.createEmptyContext();
					context.setAuthentication(authentication);
					SecurityContextHolder.setContext(context);
				}
				}
			} catch (JwtException | IllegalArgumentException exception) {
				SecurityContextHolder.clearContext();
			}
			
			filterChain.doFilter(request, response);
		}
}
