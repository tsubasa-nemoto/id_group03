package com.example.demo;

import java.util.Collections;

import javax.servlet.SessionTrackingMode;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class config implements WebMvcConfigurer{
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/img/**")
		 .addResourceLocations("file:///C:/pleiades/workspace/id_group03/src/main/resources/templates/img/");
	 }
	   @Bean
	    public ServletContextInitializer servletContextInitializer() {
	        // 初回アクセス時に、URLにSessionIDが付与されるのを防ぐ
	        // https://blog.ik.am/entries/353
	        ServletContextInitializer initializer = servletContext -> {
	            servletContext.setSessionTrackingModes(
	                Collections.singleton(SessionTrackingMode.COOKIE)
	            );
	        };
	        return initializer;
	    }
}
