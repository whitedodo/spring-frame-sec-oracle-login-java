/*
 * 	프로젝트명(Project Name): Spring-Security 5 with Java Project(Spring MVC)
 * 	파일명(Filename): SecurityConfig.java
 * 	주제(Subject): Spring Security 환경 설정(security-config.xml 영역과 동일함.)
 * 	작성일자(Create Date): 2020-09-27
 * 	저자(Author): 도도(Dodo) / rabbit.white at daum dot net
 * 	설명(Description): 
 * 	1. Spring Security에서 사용함., 도도(Dodo), 2020-09-27
 * 	2. 필수 영역, 도도(Dodo), 2020-09-27
 */

package com.springMVC.javaSecurity5.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.springMVC.javaSecurity5.db.SqlMapSessionFactory;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    private CustomAuthenticationProvider authProvider;
    
    protected void configure(AuthenticationManagerBuilder auth, HttpSecurity http) throws Exception {
      
    	CharacterEncodingFilter filter = new CharacterEncodingFilter();

    	/* UTF-8 한글 보완 */
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);
    	
        auth
        	.authenticationProvider(authProvider);
        
    	/* 현재 - 임시
        auth.inMemoryAuthentication()
        .passwordEncoder(passwordEncoder)
        .withUser("user").password(passwordEncoder.encode("1234")).roles("RULE_USER")
        .and()
        .withUser("admin").password(passwordEncoder.encode("1234")).roles("RULE_USER", "RULE_ADMIN");
        
         */
        
        // withUser("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN");
      
        /* 임시
    	
    	UserBuilder users = User.withDefaultPasswordEncoder();
	      auth.inMemoryAuthentication()
	        .withUser(users.username("admin").password("1234").roles("USER"))
	        .withUser(users.username("user").password("1234").roles("ADMIN"));
	    */
    }
 
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
        
        	 // index
	        .antMatchers("/")
	            .permitAll()

	         // 접근 오류
		     .antMatchers("/member/accessDenied")
		         .permitAll()
		         
	         .antMatchers("/member/accessDeniedView")
		         .permitAll()    

	         // 회원 로그인 기능
		     .antMatchers("/member/loginForm")
			         .permitAll()
		         
		     // 관리자 페이지 기능
	        .antMatchers("/admin/**")
	        	.hasRole("ADMIN")
	        	// "RULE_ADMIN이라고 DB에 입력되어 있다면, RULE_은 제거하고 입력해야 인식함."
	        
	        // 폼 로그인 명세
	        .and()
	            .formLogin()
        			.permitAll()
    	            .loginPage("/member/loginForm")
    	            .failureForwardUrl("/member/loginForm?error")
    	            .defaultSuccessUrl("/")
    	            .usernameParameter("id")
    	            .passwordParameter("password")
        	
        	// 로그아웃 처리
			.and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSION_ID")
                .deleteCookies("remember-me")
        	// 로그인 
            .and()
            	.rememberMe()
            		.tokenValiditySeconds(604800)
            		.tokenRepository(persistentTokenRepository())
            // 예외처리(
            .and()
        		.exceptionHandling()
        		.accessDeniedPage("/member/accessDenied")
        	// csrf 설정
            .and()
                .csrf().disable();
        	
        
            
        
    	/*
        http.authorizeRequests()
	        .antMatchers("/login")
	            .permitAll()
	        .antMatchers("/**")
	            .hasAnyRole("ADMIN", "USER")
	            .hasAnyAuthority("RULE_ADMIN", "RULE_USER")
	        .and()
	            .formLogin()
	            .loginPage("/login")
	            .defaultSuccessUrl("/")
	            .failureUrl("/login?error=true")
	            .permitAll()
	            
	            .loginPage("/login")
	            .usernameParameter("email")
	            .passwordParameter("password")
	            .successHandler(successHandler())
	            .failureHandler(failureHandler())
	            .permitAll();
	            
	        .and()
	            .logout()
	            .logoutSuccessUrl("/login?logout=true")
	            .invalidateHttpSession(true)
	            .permitAll()
	        .and()
	            .csrf()
	            .disable();
	    */
        
    }
    
    // 로그아웃 Persistent_Logins에 관한 설정
    @Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		DataSource usrDS = getDataSource();
		db.setDataSource(usrDS);
		return db;
	}
    

    // DataSource 불러오기
	@Bean
	public DataSource getDataSource() {
	       // BasicDataSource dataSource = new BasicDataSource();		- Apache DBCP2
			SqlMapSessionFactory factory = SqlMapSessionFactory.getInstance();
	       return factory.getOracleDataSource();	// 오라클 적용함.
	}
    
	// 비밀번호 생성 - 암호(BCryptPasswordEncoder)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        
    }
    
}