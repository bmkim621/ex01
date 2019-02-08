package com.yi.domain;

import java.util.Date;

public class ReplyVO {
	public int rno;
	public int bno;
	public String replytext;
	public String replyer;
	public Date regdate;
	public Date updatedate;
	
	public ReplyVO() {
		// TODO Auto-generated constructor stub
	}

	public ReplyVO(int rno, int bno, String replytext, String replyer, Date regdate, Date updatedate) {
		this.rno = rno;
		this.bno = bno;
		this.replytext = replytext;
		this.replyer = replyer;
		this.regdate = regdate;
		this.updatedate = updatedate;
	}

	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getReplytext() {
		return replytext;
	}

	public void setReplytext(String replytext) {
		this.replytext = replytext;
	}

	public String getReplyer() {
		return replyer;
	}

	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	@Override
	public String toString() {
		return String.format("ReplyVO [rno=%s, bno=%s, replytext=%s, replyer=%s, regdate=%s, updatedate=%s]", rno, bno,
				replytext, replyer, regdate, updatedate);
	}
	
}
