package com.nes.tireso.base.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@PropertySource("classpath:application.yml")
@OpenAPIDefinition(
		info = @Info(title = "TIRE,SO. WEB",
				description = "TIRE,SO. WEB API 명세",
				version = "v1")
)
@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi OpenApi() {
		String[] paths = {"/**"};

		return GroupedOpenApi.builder()
				.group("TIRE,SO. API v1")
				.pathsToMatch(paths)
				.build();
	}
}