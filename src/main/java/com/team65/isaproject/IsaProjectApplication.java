package com.team65.isaproject;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
//@EnableSwagger2
public class IsaProjectApplication {

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(IsaProjectApplication.class, args);
	}

//	@Configuration
//	public static class CorsConfig {
//
//		@Bean
//		public CorsFilter corsFilter() {
//			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//			CorsConfiguration config = new CorsConfiguration();
//			config.setAllowCredentials(true);
//			config.addAllowedOrigin("http://localhost:3000"); // Replace with your React app's origin
//			config.addAllowedHeader("*");
//			config.addAllowedMethod("*");
//			source.registerCorsConfiguration("/**", config);
//			return new CorsFilter(source);
//		}
//	}

}
