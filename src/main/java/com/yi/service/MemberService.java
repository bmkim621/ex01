package com.yi.service;

import java.util.List;

import com.yi.domain.MemberVO;

public interface MemberService {
	
	public String getTime();
	
	public void insertMember(MemberVO vo);
	
	public MemberVO readMember(String userid);

	public List<MemberVO> selectAll();
	
	//아이디가 XX인 이름, 비밀번호, 이메일 변경
	public void updateMember(MemberVO vo);
	
	public void deleteMember(String userid);
	
	//아이디, 비밀번호 일치하는 회원 찾기
	public MemberVO read(String userid, String userpw);
}
