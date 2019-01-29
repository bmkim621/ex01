package com.yi.domain;

public class SearchCriteria extends Criteria {
	private String searchType;	//검색종류(t: title, c: content, w: writer)
	private String keyword;		//검색어
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Override
	public String toString() {
		return String.format("SearchCriteria [searchType=%s, keyword=%s]", searchType, keyword);
	}
	
}
