package com.board.model.service;

import static com.board.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.board.medel.dao.BoardDAO;
import com.board.medel.dto.BoardDTO;

public class BoardService {
	private BoardDAO dao=new BoardDAO();
	
	public List<BoardDTO> selectAllBoard(){
		Connection conn=getConnection();
		List<BoardDTO> boards=dao.selectAllBoard(conn);
		for(BoardDTO b : boards) {
			b.setComments(dao.selectBoardComment(conn, b.getBoardNo()));
		}
		
		
		
		close(conn);
		return boards;
	}

	public List<BoardDTO> searchBoard(Map map) {
		Connection conn=getConnection();
		List<BoardDTO> boards=dao.searchBoard(conn,map);
		close(conn);
		return boards;
	}
}
