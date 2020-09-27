/*
 * 	프로젝트명(Project Name): Spring-Security 5 with Java Project(Spring MVC)
 * 	파일명(Filename): SqlMapSessionFactory.java
 * 	주제(Subject): 연결
 * 	작성일자(Create Date): 2020-09-27
 * 	저자(Author): 도도(Dodo) / rabbit.white at daum dot net
 * 	설명(Description):
 * 
 */

package com.springMVC.javaSecurity5.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

public class SqlMapSessionFactory {
private static SqlMapSessionFactory factory = new SqlMapSessionFactory();

	private SqlMapSessionFactory() {}

	/*
     * 	  // MySQL 
     *       Class.forName("com.mysql.jdbc.Driver");
     *       String dbURL="jdbc:mysql://localhost:3306/{dbName}?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";                             
     *       String dbID="자신의dbID";
     *       String dbPassword="자신의dbPassword";
     *       
     *       // MariaDB
     *       Class.forName("org.mariadb.jdbc.Driver");
	 * 	  String dbURL = "jdbc:mariadb://localhost:3306/{dbName}?autoReconnect=true&verifyServerCertificate=false&useSSL=false"
	 * 	  String dbID="자신의dbID";
     *       String dbPassword="자신의dbPassword";
     *       
	 */
	
	private final String driverName = "oracle.jdbc.driver.OracleDriver";
	private final String dbUrl = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	private final String userName = "{사용자계정명}";
	private final String userPassword = "{비밀번호}";
	
	public static SqlMapSessionFactory getInstance() {
		return factory;
	}

	/*
	 *     public static DataSource getMySQLDataSource() {
        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource mysqlDS = null;
        try {
            fis = new FileInputStream("db.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }
    */
	
    public DataSource getOracleDataSource(){
        
    	OracleDataSource oracleDS = null;
        
    	try {
            oracleDS = new OracleDataSource();
            oracleDS.setURL(dbUrl);
            oracleDS.setUser(userName);
            oracleDS.setPassword(userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oracleDS;
        
    }
	
	public Connection connect() {

		Connection conn = null;

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbUrl, userName, userPassword);
		}
		catch(Exception ex) {
			System.out.println("오류 발생: " + ex);
		}

		return conn;

	}

	public void close(Connection conn, PreparedStatement ps, ResultSet rs) {

		if ( rs != null ) {

			try {
				rs.close();
			}
			catch(Exception ex) {
				System.out.println("오류 발생: " + ex);
			}

			close(conn, ps);	// Recursive 구조 응용(재귀 함수)

		} // end of if

	}	

	public void close(Connection conn, PreparedStatement ps) {

		if (ps != null ) {
			try {
				ps.close();
			}
			catch(Exception ex) {
				System.out.println("오류 발생: " + ex);
			}
		} // end of if

		if (conn != null ) {
			try {
				conn.close();
			}
			catch(Exception ex) {
				System.out.println("오류 발생: " + ex);
			}
		} // end of if

	}
	
}
