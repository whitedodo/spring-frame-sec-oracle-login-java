/*
 * 	프로젝트명(Project Name): Spring-Security 5 with Java Project(Spring MVC)
 * 	파일명(Filename): CustomUserDetails.java
 * 	주제(Subject): Model 영역
 * 	작성일자(Create Date): 2020-09-27
 * 	저자(Author): 도도(Dodo) / rabbit.white at daum dot net
 * 	설명(Description): 
 * 	1. 계정 기본 정보에 대한 모델, 도도(Dodo) , 2020-09-27
 */

package com.springMVC.javaSecurity5.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private String username;
    private String password;
    // 개량함. (다중 권한 고려)
    private List<GrantedAuthority> authorities;
    private boolean enabled;
        
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setAuthority(String authority) {
		
		// 권한 객체 생성
		if ( authorities == null ) {
			authorities = new ArrayList<GrantedAuthority>();
		}
		
		// 권한 추가
		SimpleGrantedAuthority grantObj = new SimpleGrantedAuthority(authority);
		authorities.add(grantObj);
		
	}

	public boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;

	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
    
}