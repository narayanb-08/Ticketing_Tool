package com.sumpositive.ticketing.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
	
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration  = new CorsConfiguration();
		
		//Allow Frontend 
		configuration.setAllowedOrigins(List.of("https://localhost:4200"));
		
		//HTTPS methods
		configuration.setAllowedOrigins(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
		
		//Allow request headers
		configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
		
		//JWT authentication uses Bearer tokens
		configuration.setAllowCredentials(false);
		
		//Apply CORS configuration to all API endpoint
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
