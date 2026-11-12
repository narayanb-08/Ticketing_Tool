package com.sumpositive.ticketing.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint  authenticationEntryPoint;
	
	public SecurityConfig(
	        JwtAuthenticationFilter jwtAuthenticationFilter,
	        JwtAuthenticationEntryPoint authenticationEntryPoint) {

	    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    this.authenticationEntryPoint = authenticationEntryPoint;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception {
		http.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception
					    .authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
						              													 .requestMatchers("/api/auth/**").permitAll()
						              													 .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
						              													 .anyRequest()	.authenticated()) 
		        .addFilterBefore(
			           jwtAuthenticationFilter,
			          UsernamePasswordAuthenticationFilter.class
			);
				return http.build();
	}			
				
				@Bean
				public PasswordEncoder  passwordEncoder() {
					return new BCryptPasswordEncoder();
				}
				
}