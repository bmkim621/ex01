package com.yi.domain;

public class Criteria {
	private int page;		//선택한 페이지 번호
	private int perPageNum;	//한 페이지 당 몇 개씩 읽을 것인지 결정하는 변수
	
	public Criteria() {
		// TODO Auto-generated constructor stub
		page = 1;	//default 값을 정해놓는다.
		perPageNum = 10;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		//예외처리
		if(page <= 0) {
			page = 1;
		}
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		//예외처리
		if(perPageNum <= 0 || perPageNum > 100) {
			perPageNum = 10;	//0보다 작거나 100보다 크면 default값인 10으로 설정하도록 처리
		}
		this.perPageNum = perPageNum;
	}
	
	public int getPageStart() {
		//게시물 몇 개씩 읽어들일것인지? ex)한 페이지 당 10개 게시물 보기, ..
		return (this.page - 1) * perPageNum;
	}

	@Override
	public String toString() {
		return String.format("Criteria [page=%s, perPageNum=%s]", page, perPageNum);
	}
	
	
}
