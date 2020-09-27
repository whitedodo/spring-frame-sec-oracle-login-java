/*
 * 	프로젝트명(Project Name): Spring-Security 5 with Java Project(Spring MVC)
 * 	파일명(Filename): HomeController.java
 * 	주제(Subject): Controller (MVC)
 * 	작성일자(Create Date): 2020-09-27
 * 	저자(Author): 도도(Dodo) / rabbit.white at daum dot net
 * 	설명(Description): 
 * 	1. 홈 - 페이지 영역에 대한 정의 , 도도(Dodo) , 2020-09-27
 */

package com.springMVC.javaSecurity5.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/")
	public String home(Locale locale, Model model, HttpServletRequest req) {

		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		String password = null;

		try {
			UserDetails userDetails = (UserDetails)principal;
			
			username = userDetails.getUsername();
			password = userDetails.getPassword();
			
		}catch(Exception e) {
			e.getStackTrace();
		}
		
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		model.addAttribute("serverTime", formattedDate );

		return "home";
	}
	
	@RequestMapping("/admin")
	public String admin(Locale locale, Model model) {
		
		return "admin";
	}

	@RequestMapping(value = "/encode-password", method = RequestMethod.GET)
	public String passwordEncode(Locale locale, Model model, HttpServletRequest req) {

		// 1. xml 방식에서 java 방식으로 전환
		// web.xml 파일 제거로 인한 사용 불가(ServletConfig sc 연결됨)
		// WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());
        // PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
        String password = req.getParameter("password");
        String encode = passwordEncoder.encode(password);

        model.addAttribute("encode", encode);
        System.out.println(encode);
        
        return "home";
        
	}
	

}
