package xyz.itwill.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xyz.itwill.dto.BoardDTO;

public class BoardDAO extends JdbcDAO {
	private static BoardDAO _dao;
	
	private BoardDAO() {
		// TODO Auto-generated constructor stub
	}
	
	static {
		_dao=new BoardDAO();
	}
	
	public static BoardDAO getDAO() {
		return _dao;
	}
	
	/*
	//BOARD 테이블에 저장된 전체 게시글의 갯수를 검색하여 반환하는 메소드
	public int selectBoardCount() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int count=0;
		try {
			con=getConnection();
			
			String sql="select count(*) from board";
			pstmt=con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("[에러]selectBoardCount() 메서드의 SQL 오류 = "+e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return count;
	}
	*/

	//검색 관련 정보를 전달받아 BOARD 테이블에 저장된 특정 게시글의 갯수를 검색하여 반환하는 메소드
	public int selectBoardCount(String search, String keyword) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int count=0;
		try {
			con=getConnection();
			
			//매개변수에 저장된 값을 비교하여 다른 SQL 명령을 전달하여 실행되도록 작성
			// => 동적 SQL(Dynamic SQL) 기능 사용
			if(keyword.equals("")) {//검색 기능을 사용하지 않은 경우
				String sql="select count(*) from board";
				pstmt=con.prepareStatement(sql);
			} else {//검색 기능을 사용한 경우
				//검색대상(컬럼명)은 값이 아니므로 ?(InParameter)로 사용 불가능
				String sql="select count(*) from board join member on board.id=member.id"
						+ " where "+search+" like '%'||?||'%'";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, keyword);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("[에러]selectBoardCount() 메서드의 SQL 오류 = "+e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return count;
	}
	
	//검색 관련 정보 및 요청 페이지에 대한 시작 게시글의 행번호와 종료 게시글의 행번호를 전달
	//받아 BAORD 테이블에 저장된 특정 게시글에서 해당 범위의 게시글만을 검색하여 반환하는 메소드
	//public List<BoardDTO> selectBoardList(int startRow, int endRow) {
	public List<BoardDTO> selectBoardList(int startRow, int endRow, String search, String keyword) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<BoardDTO> boardList=new ArrayList<>();
		try {
			con=getConnection();
			
			if(keyword.equals("")) {
				String sql="select * from (select rownum rn,temp.* from (select num,member.id"
					+ ",name,subject,reg_date,readcount,ref,re_step,re_level,content,ip"
					+ ",board.status from board join member on board.id=member.id"
					+ " order by ref desc, re_step) temp) where rn between ? and ?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
			} else {
				String sql="select * from (select rownum rn,temp.* from (select num,member.id"
						+ ",name,subject,reg_date,readcount,ref,re_step,re_level,content,ip"
						+ ",board.status from board join member on board.id=member.id"
						+ " where "+search+" like '%'||?||'%' and board.status=1 order by"
						+ " ref desc, re_step) temp) where rn between ? and ?";
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, keyword);
					pstmt.setInt(2, startRow);
					pstmt.setInt(3, endRow);
			}
			
			rs=pstmt.executeQuery();
					
			while(rs.next()) {
				BoardDTO board=new BoardDTO();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));
				board.setWriter(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setRegDate(rs.getString("reg_date"));
				board.setReadcount(rs.getInt("readcount"));
				board.setRef(rs.getInt("ref"));
				board.setReStep(rs.getInt("re_step"));
				board.setReLevel(rs.getInt("re_level"));
				board.setContent(rs.getString("content"));
				board.setIp(rs.getString("ip"));
				board.setStatus(rs.getInt("status"));
				boardList.add(board);
			}
		} catch (SQLException e) {
			System.out.println("[에러]selectBoardList() 메서드의 SQL 오류 = "+e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return boardList;
	}
	
	//BOARD_SEQ 시퀸스의 다음값을 검색하여 반환하는 메소드
	public int selectNextNum() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int nextNum=0;
		try {
			con=getConnection();
			
			String sql="select board_seq.nextval from dual";
			pstmt=con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				nextNum=rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("[에러]selectNextNum() 메서드의 SQL 오류 = "+e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return nextNum;
	}
	
	//게시글을 전달받아 BOARD 테이블에 삽입하고 삽입행의 갯수를 반환하는 메소드
	public int insertBoard(BoardDTO board) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int rows=0;
		try {
			con=getConnection();
			
			String sql="insert into board values(?,?,?,sysdate,0,?,?,?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, board.getNum());
			pstmt.setString(2, board.getId());
			pstmt.setString(3, board.getSubject());
			pstmt.setInt(4, board.getRef());
			pstmt.setInt(5, board.getReStep());
			pstmt.setInt(6, board.getReLevel());
			pstmt.setString(7, board.getContent());
			pstmt.setString(8, board.getIp());
			pstmt.setInt(9, board.getStatus());
			
			rows=pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[에러]insertBoard() 메서드의 SQL 오류 = "+e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
}














