package com.yi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.domain.ReplyVO;
import com.yi.service.BoardService;
import com.yi.service.ReplyService;

//RestController : 값만 주고받음
@RestController
@RequestMapping("/replies/*")
public class ReplyController {
	//로그로 확인하기
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Autowired
	private ReplyService service;
	
	@Autowired
	private BoardService bService;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	//@RequestBody : RestController에서 post로 받기 위해서는 @RequestBody가 필요함.
	//get은 주소창에 ?키=값으로 보냄(매개변수 실어서 보낸다), post는 body에 값이 실림. 그러므로 body가 있다는 @RequestBody가 필요함.(rest에서 post에서만!)
	public ResponseEntity<String> register( @RequestBody ReplyVO vo){
		
		ResponseEntity<String> entity = null;
		
		logger.info("===== Reply Create ===== " + vo);
		
		try {
			service.create(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);	//성공 시 success와 함께 OK 보냄.
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);	//실패 시 에러 원인과 400 에러를 같이 보냄.
		}
		
		return entity;
	}
	
	//페이지
	//ResponseEntity를 사용해야 400 에러인지, OK인지 확인할 수 있음.
	@RequestMapping(value = "/{bno}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPage( @PathVariable("bno") int bno, @PathVariable("page") int page){
		
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			//페이지 당 댓글 몇 개 나오개 할건지 결정
			Criteria cri = new Criteria();
			cri.setPage(page);
			
			List<ReplyVO> list = service.listPage(cri, bno);
			
			//페이지 정보
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			
			//댓글 총 갯수
			int count = service.totalCount(bno);
			pageMaker.setTotalCount(count);
			
			//댓글 수
			BoardVO bVO = bService.read(bno);
			
			System.out.println(bVO.getReplycnt());
			//그냥 Map = new Hashmap<> 사용하면 됨.
			//댓글에 보이는 list정보와 페이지 정보가 같이 넘어가야되니까 map 사용해야 함.
			HashMap<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("pageMaker", pageMaker);
			//댓글 수 가지고오기
			map.put("replycnt", bVO.getReplycnt());
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	
	
	
	//댓글 보기
	//주소에 있는 {bno}를 메서드에 있는 매개변수에다가 바로 넣을 수 있음 => @PathVariable
	//@PathVariable : 주소에 있는 url 변수를 내 매개변수에 대입시켜준다.
	@RequestMapping(value = "/all/{bno}", method = RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") int bno){
		
		ResponseEntity<List<ReplyVO>> entity = null;
		
		try {
			//리스트 가져오기
			List<ReplyVO> list = service.list(bno);
			//실어서 보내기
			entity = new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//e.getMessage()는 String으로 보내야하는데 여기에서는 List로 받아야되기 때문에, 못 바꾸면 그냥 httpstatus만 보낸다.
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//댓글 수정
	//put => post와 마찬가지로 body로 보내짐. 수정되는 내용이 ReplyVO에 있으니까 @RequestBody 앞에 붙여야 함.
	@RequestMapping(value = "{rno}", method = RequestMethod.PUT)
	public ResponseEntity<String> update(@PathVariable("rno") int rno, @RequestBody ReplyVO vo){
		
		ResponseEntity<String> entity = null;
		
		try {
			vo.setRno(rno);
			service.update(vo);
			
			entity = new ResponseEntity<String>("Success", HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	
	//댓글 삭제
	//get, delete => 주소줄에 실어서 보냄.
	@RequestMapping(value = "{rno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("rno") int rno){
		
		ResponseEntity<String> entity = null;
		
		try {
			service.delete(rno);
			entity = new ResponseEntity<String>("Success", HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}
