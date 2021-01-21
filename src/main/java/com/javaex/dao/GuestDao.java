package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestVo;
@Repository
public class GuestDao {
	@Autowired//데이터소스를 알아서 관리해줌
	private DataSource dataSource;
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private int count = 0;
	
	private void getConnection() {
		try {
			conn = dataSource.getConnection();//알아서 비어있는걸준다.
		} catch (SQLException e) {//null처리는 알아서 해줄거임
			System.out.println("error:" + e);
		}
	}
	
	private void close() {
		// 5. 자원정리
	    try {
	        if (rs != null) {
	            rs.close();
	        }            	
	    	if (pstmt != null) {
	        	pstmt.close();
	        }
	    	if (conn != null) {
	        	conn.close();
	        }
	    } catch (SQLException e) {
	    	System.out.println("error:" + e);
	    }
	}
	
	public List<GuestVo> getList() {
		
		List<GuestVo> guList = new ArrayList<GuestVo>();
		
		getConnection();
		
		try {		
			// 3. SQL문 준비 / 바인딩 / 실행
			String query="";
			query +=" select	no,";
			query +="			name,";
			query +="			password,";
			query +="			content,";
			query +="			reg_date";
			query +=" from guestbook";
			
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regdate = rs.getString("reg_date");
				GuestVo guVo = new GuestVo(no, name, password, content, regdate);
				
				guList.add(guVo);
			}
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		
		return guList;
	}
	
	public int guestinsert(GuestVo guVo) {
		
		getConnection();
		
		try {		
			// 3. SQL문 준비 / 바인딩 / 실행
			String query="";
			query +=" insert into guestbook";
			query +=" values (seq_guest_id.nextval, ?, ?, ?, sysdate)";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, guVo.getName());
			pstmt.setString(2, guVo.getPassword());
			pstmt.setString(3, guVo.getContent());
			
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("[DAO] : " +count+ "건 등록");
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		
		return count;
	}
	
	public int guestdelete(GuestVo guVo) {
		
		getConnection();
		
		try {		
			// 3. SQL문 준비 / 바인딩 / 실행
			String query="";
			query +=" DELETE FROM guestbook WHERE no = ? ";
			query +=" and password =?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, guVo.getNo());
			pstmt.setString(2, guVo.getPassword());
			
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("[DAO] : " +count+ "건 삭제");
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		
		return count;
		
	}
	
	
}