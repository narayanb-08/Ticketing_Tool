package com.sumpositive.ticketing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI ticketingOpenAPI() {
		return new OpenAPI()
							.info(new Info()
										.title("Sumpositive Ticketing Tool API")
										.description("Rest API documentation for the Sumpositive Ticketing Tool.")
										.version("1.0.0")
									);
							
	}
}
