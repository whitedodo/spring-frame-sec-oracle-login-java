/*
 * 	프로젝트명(Project Name): Spring-Security 5 with Java Project(Spring MVC)
 * 	파일명(Filename): WebConfig.java
 * 	주제(Subject): Spring Framework의 Web Config
 * 	작성일자(Create Date): 2020-09-27
 * 	저자(Author): 도도(Dodo) / rabbit.white at daum dot net
 * 	설명(Description): 
 * 	1. Spring Framework에서 사용함., 도도(Dodo), 2020-09-27
 * 	2. web-xml 필수 영역, 도도(Dodo), 2020-09-27
 */

package com.springMVC.javaSecurity5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
 
@EnableWebMvc // <mvc:annotation-driven>에 해당.
// @ComponentScan(basePackages = {"com.figo.web"})  // <context:component-scan base-package="”com.figo.web”/">에 해당됨.
@ComponentScan("com.springMVC.javaSecurity5")
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
 
    // <resources mapping="/resources/**" location="/resources/">에 해당됨.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
    }
 
    // <mvc:default-servlet-handler>에 해당됨.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }   
     
    // web.xml에서 봤던 내용들임.
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    
    /*
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	
        // registry.addViewController("/web").setViewName("home");
        registry.addViewController("/").setViewName("home");
        
    }
    */
 
}