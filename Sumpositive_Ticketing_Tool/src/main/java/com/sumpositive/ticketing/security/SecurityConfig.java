package com.sumpositive.ticketing.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint  authenticationEntryPoint;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,JwtAuthenticationEntryPoint authenticationEntryPoint) {
	    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    this.authenticationEntryPoint = authenticationEntryPoint;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception {
		http.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception
					    .authenticationEntryPoint(authenticationEntryPoint)
	            )
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
						              													 .requestMatchers("/api/auth/**").permitAll()
						              													 .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
						              													 .requestMatchers("/api/dashboard/**").hasAnyRole("ADMIN", "PROJECT_MANAGER", "HR", "TEAM_LEADER", "DEVELOPER", "TESTER", "SUPPORT_ENGINEER", "EMPLOYEE") //Accessing Dashboard
						              													 .requestMatchers("/api/users/**").hasRole("ADMIN") //user Administration
						              													 .requestMatchers(HttpMethod.DELETE, "/api/tickets/**").hasAnyRole("ADMIN", "MANAGER")
						              													 .requestMatchers("/api/tickets/**").hasAnyRole("ADMIN", "PROJECT_MANAGER", "HR", "TEAM_LEADER", "DEVELOPER", "TESTER", "SUPPORT_ENGINEER", "EMPLOYEE")   // Accessing Ticket
						              													.requestMatchers("/api/admin/setup").permitAll()
						              													.requestMatchers("/api/admin/**").hasRole("ADMIN")
						              													 .anyRequest().authenticated()
						              													 ) 
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