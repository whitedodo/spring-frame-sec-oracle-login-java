/*
 * 	프로젝트명(Project Name): Spring-Security 5 with Java Project(Spring MVC)
 * 	파일명(Filename): AdminController.java
 * 	주제(Subject): Controller (MVC)
 * 	작성일자(Create Date): 2020-09-27
 * 	저자(Author): 도도(Dodo) / rabbit.white at daum dot net
 * 	설명(Description): 
 * 	1. 관리자 페이지 영역에 대한 정의 , 도도(Dodo) , 2020-09-27
 */

package com.springMVC.javaSecurity5.controller;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome - 관리자 페이지(Admin Home)!");

		return "admin/home";
	}

}
