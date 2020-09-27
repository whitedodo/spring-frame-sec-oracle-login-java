/*
 * 	프로젝트명(Project Name): Spring-Security 5 with Java Project(Spring MVC)
 * 	파일명(Filename): SqlSessionTemplate.java
 * 	주제(Subject): 로그인 권한
 * 	작성일자(Create Date): 2020-09-27
 * 	저자(Author): 도도(Dodo) / rabbit.white at daum dot net
 * 	설명(Description):
 * 	1. DB 구현(Spring Security), 도도(Dodo), 2020-09-27
 * 
 */

package com.springMVC.javaSecurity5.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.springMVC.javaSecurity5.db.SqlMapSessionFactory;
import com.springMVC.javaSecurity5.model.CustomUserDetails;

public class SqlSessionTemplate {

	private SqlSessionTemplate() {}
	private static SqlSessionTemplate sqlTemplate;
    private static SqlMapSessionFactory session; 
    
    public static SqlSessionTemplate getInstance(){
    	
        if(sqlTemplate == null){
        	sqlTemplate = new SqlSessionTemplate();
            session = SqlMapSessionFactory.getInstance();
        }

        return sqlTemplate;
    }
    
	// 추후 iBatis 고려
	public CustomUserDetails selectOne(String id, String username) {
		
		Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	CustomUserDetails node = null;

    	String sql = "select g1.username, g1.password, g2.authority, " + 
    					   "g1.enabled from comp_users g1, comp_authorities g2 where g1.username = g2.username " +
    					   "and g1.username = ?";

    	System.out.println(sql);

    	try {

    		conn = session.connect();

    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, username);
    	
    		rs = pstmt.executeQuery();

    		while ( rs.next() ) {
    			
    			// 데이터가 존재할 때, 노드 생성
    			node = new CustomUserDetails();
    			node.setUsername(rs.getNString(1));
    			node.setPassword(rs.getNString(2));
    			node.setAuthority(rs.getNString(3));
    			System.out.println("rs:" + rs.getNString(3));
    			node.setEnabled(rs.getBoolean(4));
    		}

    	}catch(Exception ex) {
    		System.out.println("오류 발생: " + ex);
    	}
    	finally {
    		session.close(conn, pstmt, rs);
    	}

    	return node;
		
	}
	
}
