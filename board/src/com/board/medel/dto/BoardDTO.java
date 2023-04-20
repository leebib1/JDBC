package com.board.medel.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardDTO {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWritter; //자바 작성 방법대로 쓸 땐 Member를 불러와야한다.
	private Date date;
	private List<CommentDTO> comments=new ArrayList();
	
	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

	public BoardDTO(int boardNo, String boardTitle, String boardContent, String boardWritter, Date date,
			List<CommentDTO> comments) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWritter = boardWritter;
		this.date = date;
		this.comments = comments;
	}

	public BoardDTO() {
		// TODO Auto-generated constructor stub
	}

	public BoardDTO(int boardNo, String boardTitle, String boardContent, String boardWritter, Date date) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWritter = boardWritter;
		this.date = date;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardWritter() {
		return boardWritter;
	}

	public void setBoardWritter(String boardWritter) {
		this.boardWritter = boardWritter;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "BoardDTO [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", boardWritter=" + boardWritter + ", date=" + date + ", comments=" + comments + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(boardContent, boardNo, boardTitle, boardWritter, comments, date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardDTO other = (BoardDTO) obj;
		return Objects.equals(boardContent, other.boardContent) && boardNo == other.boardNo
				&& Objects.equals(boardTitle, other.boardTitle) && Objects.equals(boardWritter, other.boardWritter)
				&& Objects.equals(comments, other.comments) && Objects.equals(date, other.date);
	}

	
	
	
}
