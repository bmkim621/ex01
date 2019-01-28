package com.yi.domain;

public class PageMaker {
	private int totalCount;	//게시물 전체 갯수
	private int startPage;	//현재 보이는 페이지의 시작 번호
	private int endPage;	//현재 보이는 페이지의 끝 번호
	private boolean prev;	//이전 10개 존재여부
	private boolean next;	//이후 10개 존재여부
	
	private int displayPageNum = 10;	//화면에 보이는 페이지들의 갯수
	
	private Criteria cri;

	//
	private void calcData() {
		//ex)게시물이 총 151개가 있다고 가정한다.
		//page 번호를 15번 선택한다.
		
		//현재 페이지의 끝 번호를 구한다. ex) 15번 페이지(내가 선택한 페이지)에 게시물 10개씩 보이면? ===> Math.ceil(150/10 => 1.5를 올림 처리하면 2), 따라서 2 * 10 = 20페이지
		endPage = (int) ( Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum );
		
		// * 현재 페이지의 시작 번호를 구한다. ex) 20 - 10 + 1 = 11
		startPage = ( endPage - displayPageNum ) + 1;	//게시물 10개 보이게 하고 싶으면? 20-10 = 10인데 시작 페이지가 11부터 나와야 되니까 +1 시켜줌.
		
		// * cri.getPerPageNum() : 한 페이지에 보여질 게시물 갯수
		// * 전체 게시물이 151이고, 현재 페이지가 15일 때 마지막 endPage는 16이 나타나야한다.
		// * ex) Math.ceil(151 / 10) = 16
		//실제로 구한 게시물의 페이지 끝 번호(중간에 글 삭제될 수도 있으니까 전체 게시물 수를 이용해서 계산한다.) => 151개면 16페이지가 끝이어야 함.
		int tempEndPage = (int) ( Math.ceil( totalCount / (double) cri.getPerPageNum() ));
		
		// * 만약, 끝 페이지 번호(endPage)가 실제 구해진 끝 번호(tempEndPage)보다 크면 변경해준다.
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		// * 이전 페이지 번호
		//첫번째 페이지에서는 이전 페이지로 가는 버튼 없어야되니까 false 처리
		prev = (startPage == 1) ? false : true;
		
		// * 이후 페이지 번호
		next = (endPage * cri.getPerPageNum() >= totalCount) ? false : true;
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		//페이지 계산한 메서드 호출시킨다.
		calcData();
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	
}
