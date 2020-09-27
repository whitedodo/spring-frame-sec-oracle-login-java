/*
 * 	프로젝트명(Project Name): Spring-Security 5 with Java Project(Spring MVC)
 * 	파일명(Filename): CustomUserDetailsService.java
 * 	주제(Subject): Service 영역
 * 	작성일자(Create Date): 2020-09-27
 * 	저자(Author): 도도(Dodo) / rabbit.white at daum dot net
 * 	설명(Description): 
 * 	1. 데이터영역의 간접 접근, 도도(Dodo) , 2020-09-27
 */


package com.springMVC.javaSecurity5.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.springMVC.javaSecurity5.dao.SqlSessionTemplate;
import com.springMVC.javaSecurity5.model.CustomUserDetails;

public class CustomUserDetailsService implements UserDetailsService {
    
    private SqlSessionTemplate sqlSession = SqlSessionTemplate.getInstance();
 
    public CustomUserDetails getUserById(String username) {
        return sqlSession.selectOne("user.selectUserById", username);
    }
 
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    	CustomUserDetails user = sqlSession.selectOne("null", username);
        
        if(user==null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
    
}