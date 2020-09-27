/*
 * 	프로젝트명(Project Name): Spring-Security 5 with Java Project(Spring MVC)
 * 	파일명(Filename): CustomAuthenticationProvider.java
 * 	주제(Subject): 커스텀 인증 토큰
 * 	작성일자(Create Date): 2020-09-27
 * 	저자(Author): 도도(Dodo) / rabbit.white at daum dot net
 * 	설명(Description): Spring Security에서 사용함.
 * 
 */

package com.springMVC.javaSecurity5.config;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.springMVC.javaSecurity5.service.CustomUserDetailsService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private UserDetailsService userDeSer;
 
    @Override
    public Authentication authenticate(Authentication authentication) {
        
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        
        if ( username.equals("fail")) {
        	System.out.println("(에러)아이디: 실패");
        	return null;
        }
        
        // DB 정보 읽기
        userDeSer = new CustomUserDetailsService();
        UserDetails userDetail = userDeSer.loadUserByUsername(username);
        @SuppressWarnings("unchecked")
		List<GrantedAuthority> roles = (List<GrantedAuthority>) userDetail.getAuthorities();
        
        // 권한
        System.out.println("DB불러오기-권한:" + userDetail.getAuthorities());
        System.out.println("DB불러오기-비밀번호:" + userDetail.getPassword());
        System.out.println("roles:" + roles.get(0));
        
        if ( !matchPassword(password, userDetail.getPassword())) {
        	System.out.println("(에러)비밀번호: 불일치" + password);
        	return null;
        }
        
        UsernamePasswordAuthenticationToken result =
        		new UsernamePasswordAuthenticationToken(username, password, roles);
        
        result.setDetails(userDetail);
        
        return result;
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
    
    private boolean matchPassword(String loginPwd, String password) {
    	
    	BCryptPasswordEncoder secure = new BCryptPasswordEncoder();
    	
        return secure.matches(loginPwd, password);
    }
 
}