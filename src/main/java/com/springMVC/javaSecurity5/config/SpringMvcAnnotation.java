/*
 * 	프로젝트명(Project Name): Spring-Security 5 with Java Project(Spring MVC)
 * 	파일명(Filename): SpringMvcAnnotation.java
 * 	주제(Subject): Spring MVC Annotation
 * 	작성일자(Create Date): 2020-09-27
 * 	저자(Author): 도도(Dodo) / rabbit.white at daum dot net
 * 	설명(Description): 
 * 	1. Spring Framework에서 사용함., 도도(Dodo), 2020-09-27
 * 	2. web-xml 필수 영역, 도도(Dodo), 2020-09-27
 */

package com.springMVC.javaSecurity5.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcAnnotation extends AbstractAnnotationConfigDispatcherServletInitializer {

	  @Override
	  protected Class<?>[] getRootConfigClasses() {
	    return null;
	  }

	// bean 설정과 spring container 설정을 위한 Config 클래스를 등록한다.
	// Config 클래스는 web.xml의 dispatcher servlet 초기화에 사용된 xml과 같은 기능을 한다.
	  @Override
	  protected Class<?>[] getServletConfigClasses() {
	    return new Class[] { WebConfig.class };
	  }

	// web.xml의 servlet mapping 부분을 대체한다.
	  @Override
	  protected String[] getServletMappings() {
	    return new String[] { "/" };
	  }
}