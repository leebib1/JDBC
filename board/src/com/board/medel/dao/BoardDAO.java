package com.board.medel.dao;

import static com.board.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.board.medel.dto.BoardDTO;
import com.board.medel.dto.CommentDTO;

public class BoardDAO {
	private Properties sql=new Properties();
	{
		String path=BoardDAO.class.getResource("/sql.properties").getPath();
		try(FileReader fr=new FileReader(path)) {
			sql.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<BoardDTO> selectAllBoard(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=this.sql.getProperty("selectAllBoard");
		List<BoardDTO> boards=new ArrayList();
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				//boards.add(new BoardDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5)));
				BoardDTO b=getBoard(rs);
				b.setComments(selectBoardComment(conn, b.getBoardNo()));
				//->아래 메소드를 따로 실행하지 않고 한 번에 구해도 된다.
				boards.add(getBoard(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return boards;
	}
	
	private BoardDTO getBoard(ResultSet rs) throws SQLException{
		BoardDTO b=new BoardDTO();
		b.setBoardNo(rs.getInt("board_no"));
		b.setBoardTitle(rs.getString("board_title"));
		b.setBoardContent(rs.getString("board_content"));
		b.setBoardWritter(rs.getString("board_writer"));
		b.setDate(rs.getDate("board_date"));
		return b;
	}
	
	public List<CommentDTO> selectBoardComment(Connection conn, int boardNo){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<CommentDTO> comments=new ArrayList();
		String sql=this.sql.getProperty("selectBoardCommentByBoardNo");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				comments.add(getComment(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return comments;
	}

	private CommentDTO getComment(ResultSet rs) throws SQLException{
		CommentDTO c=new CommentDTO();
		c.setCommentNo(rs.getInt("coment_no"));
		c.setCommentComent(rs.getString("coment_coment"));
		c.setCommentWriter(rs.getString("coment_writer"));
		c.setCommentDate(rs.getDate("coment_date"));
		
		return c;
	}

	public List<BoardDTO> searchBoard(Connection conn, Map map) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<BoardDTO> boards=new ArrayList();
		String sql=this.sql.getProperty("selectBoardByCol");
		sql=sql.replace("#COL", (String)map.get("col"));
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+(String)map.get("keyword")+"%");
			rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO b=getBoard(rs);
				b.setComments(selectBoardComment(conn, b.getBoardNo()));
				boards.add(b);
			}
	
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return boards;
	}
}
