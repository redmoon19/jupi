package com.kedu.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kedu.member.dto.MemberDto;
import com.kedu.member.util.DBManager;

public class MemberDao {
	
	private MemberDao(){
	}
	
	private static MemberDao instance = new MemberDao();
	
	public static MemberDao getInstance(){
		if(instance == null) {
			instance = new MemberDao();
		}
		return instance;
	}
	
	public List<MemberDto> selectAllMember() {
		String sql = "select * from mem order by no ";
		
		List<MemberDto> memberlist = new ArrayList<MemberDto>();
		Connection conn = null;
		Statement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.createStatement();
			rs = pstmt.executeQuery(sql);
			
			while (rs.next()) {
				MemberDto mDto = new MemberDto(/* rs.getInt("no")
											  , rs.getString("name")
											  , rs.getString("email")
											  , rs.getString("password")*/
											  );
				
				mDto.setNo(rs.getInt("no"));
				mDto.setName(rs.getString("name"));
				mDto.setEmail(rs.getString("email"));
				mDto.setPassword(rs.getString("password"));
				
				memberlist.add(mDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return memberlist;
	}
	
	public MemberDto selectOneByNo(int no) {
		/*String sql = "select * from mem where no = ? ";*/
		
		MemberDto mDto = new MemberDto();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from mem ");
		sql.append(" where no= ? ");
		
		try {
			conn = DBManager.getConnection();
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mDto = new MemberDto(/*rs.getInt("no")
								  , rs.getString("name")
								  , rs.getString("email")
								  , rs.getString("password")*/
								  );
				
				mDto.setNo(rs.getInt("no"));
				mDto.setName(rs.getString("name"));
				mDto.setEmail(rs.getString("email"));
				mDto.setPassword(rs.getString("password"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return mDto;
	}
	
	
	public int updateMember(MemberDto mDto) {
		/*String sql = "update mem set name=?, email=?, password=?"
				+ " where no = ? ";*/
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = -1;
		StringBuilder sql = new StringBuilder();
		sql.append("update mem");
		sql.append(" set mane = " + mDto.getName());
		sql.append(" , email= " + mDto.getEmail());
		sql.append(" , password = " + mDto.getPassword());
		sql.append(" where no = ? ");
		
		try {
			conn = DBManager.getConnection();
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, mDto.getNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
	
	public int insertMember(MemberDto mDto) {
		int result = -1;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("insert into mem");
		sql.append(" values(mem_no_seq.nextval");
		sql.append(", ?");
		sql.append(", ?");
		sql.append(", ? )");
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, mDto.getName());
			pstmt.setString(2, mDto.getEmail());
			pstmt.setString(3, mDto.getPassword());
			result = pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
	
	public int deleteMember(int no) {
		int result = -1;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = new StringBuilder();
		sql.append("delete from mem 	");
		sql.append("where no = ? 		");
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
	
	public boolean checkPass(int no, String password) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("select password from mem ");
		sql.append(" where 1 = 1 ");
		sql.append(" and no = ? ");
		sql.append(" and password = ? ");
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = true;
			} else {
				result = false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return result;
	}
	
	@SuppressWarnings("resource")
	public MemberDto lastInsert() {
		MemberDto mDto = new MemberDto();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int lastSeq = 0;
		
		StringBuilder lastsql = new StringBuilder();
		lastsql.append("select mem_no_seq.currval as Lastseq from dual"	);
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from mem where no= ?");
		
		try{
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(lastsql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				lastSeq = rs.getInt("lastseq");
			}
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, lastSeq);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mDto = new MemberDto(/*rs.getInt("no")
									, rs.getString("name")
									, rs.getString("email")
									, rs.getString("password")*/);
				mDto.setNo(rs.getInt("no"));
				mDto.setName(rs.getString("name"));
				mDto.setEmail(rs.getString("email"));
				mDto.setPassword(rs.getString("password"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return mDto;
	}

}
