package com.board.controller;

import java.util.List;
import java.util.Map;

import com.board.common.BoardControllerInterface;
import com.board.medel.dto.BoardDTO;
import com.board.model.service.BoardService;
import com.board.view.MainView;

public class BoardController implements BoardControllerInterface{
	private BoardService service=new BoardService();
	private MainView view=new MainView();
	public void mainMenu() {
		view.mainMenu();
	}
	@Override
	public void selectAllBoard() {
		List<BoardDTO> boards=service.selectAllBoard();
		view.printAll(boards);
	}

	@Override
	public void selectSearchBoard() {
		//검색할 항목(컬럼명), 검색어
		Map map=view.inputSearch();
		List<BoardDTO> boards=service.searchBoard(map);
		view.printAll(boards);
	}

	
}
