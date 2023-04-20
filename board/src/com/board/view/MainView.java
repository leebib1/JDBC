package com.board.view;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.board.controller.BoardController;
import com.board.medel.dto.BoardDTO;

public class MainView {
	Scanner sc=new Scanner(System.in);
	
	public void mainMenu() {
		BoardController controller=new BoardController();
		int cho=0;
		while(true) {
			System.out.println("========게시글 조회========");
			System.out.println("1. 게시글 전체 조회");
			System.out.println("2. 게시글 항목 조회");
			System.out.println("0. 종료");
			System.out.print("번호 입력 : ");
			cho=sc.nextInt();
			switch(cho) {
			case 1 : controller.selectAllBoard(); break;
			case 2 : controller.selectSearchBoard();  break;
			case 0 : System.out.println("프로그램을 종료합니다."); return;
			default : System.out.println("0~3 사이의 메뉴를 입력하세요.");
			}
		}
	}
	public void printAll(List<BoardDTO> boards) {
		System.out.println("========게시글 출력===========");
		if(boards.isEmpty()) System.out.println("조회된 게시글이 없습니다.");
		else boards.forEach(b->System.out.println(""+b.getComments().size()+"개의 댓글 "+b));
		System.out.println("======================================");
	}
	public Map inputSearch() {
		System.out.println("===========게시글 항목 조회==========");
		System.out.println("조회할 항목 1.제목 / 2.내용 / 3.작성자 ");
		System.out.print("번호 입력 : ");
		int colCho=sc.nextInt();
		String col="";
		switch(colCho) {
		case 1 : col="board_title"; break;
		case 2 : col="board_content"; break;
		case 3 : col="board_writer"; break;
		}
		sc.nextLine();
		System.out.print("검색어 : ");
		String keyword=sc.nextLine();
		System.out.println("======================================");
		return Map.of("col",col,"keyword",keyword);
	}
	
	
}
