package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class config  implements WebMvcConfigurer{
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/img/**").addResourceLocations("file:///C:/pleiades/workspace/id_group03/src/main/resources/templates/img/");
	 }}
