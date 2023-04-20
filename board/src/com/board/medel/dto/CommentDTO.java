package com.board.medel.dto;

import java.sql.Date;
import java.util.Objects;

public class CommentDTO {
	private int commentNo;
	private String commentComent;
	private String commentWriter; //멤버
	private Date commentDate;
	
	public CommentDTO() {
		// TODO Auto-generated constructor stub
	}

	public CommentDTO(int commentNo, String commentComent, String commentWriter, Date commentDate) {
		super();
		this.commentNo = commentNo;
		this.commentComent = commentComent;
		this.commentWriter = commentWriter;
		this.commentDate = commentDate;
	}

	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public String getCommentComent() {
		return commentComent;
	}

	public void setCommentComent(String commentComent) {
		this.commentComent = commentComent;
	}

	public String getCommentWriter() {
		return commentWriter;
	}

	public void setCommentWriter(String commentWriter) {
		this.commentWriter = commentWriter;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	@Override
	public String toString() {
		return "CommentDTO [commentNo=" + commentNo + ", commentComent=" + commentComent + ", commentWriter="
				+ commentWriter + ", commentDate=" + commentDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(commentComent, commentDate, commentNo, commentWriter);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentDTO other = (CommentDTO) obj;
		return Objects.equals(commentComent, other.commentComent) && Objects.equals(commentDate, other.commentDate)
				&& commentNo == other.commentNo && Objects.equals(commentWriter, other.commentWriter);
	}
	
	
	
	
	
}
