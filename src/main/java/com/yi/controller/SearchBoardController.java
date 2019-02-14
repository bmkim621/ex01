package com.yi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.domain.SearchCriteria;
import com.yi.service.BoardService;
import com.yi.util.MediaUtils;
import com.yi.util.UploadFileUtils;

@Controller
@RequestMapping("/sBoard/*")
public class SearchBoardController {
	//로그
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	//주입받기(DI, Dipendency Injection)
	//@Autowired = @Inject 기능 같음.
	@Inject
	private BoardService service;
	
	//업로드 할 주소
	@Resource(name = "uploadPath")	//servlet-context에 있는 id와 일치해야 함.
	private String uploadPath;
	
	
	// url주소 : sBoard/list?page=10
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(SearchCriteria cri, Model model) {
		logger.info("======> list ----- GET");
		List<BoardVO> list = service.listSearch(cri); // cri 넣기 위해서 Model 필요

		// 페이지 하단 부분
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.searchTotalCount(cri)); // totalCount어디에서? mapper에서 처리해야함.

		logger.info("list = " + list);
		logger.info("page = " + pageMaker.getCri().getPage());
		logger.info("cri = " + cri);
		
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("cri", cri);
	}
	
	
	//게시글 등록
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public void registerGet() {
		logger.info("======> register ----- GET");
	}
	
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo, Model model, List<MultipartFile> imageFiles) throws IOException {
		logger.info("======> register ----- POST");
		
	
		List<String> files = new ArrayList<>();
		for(MultipartFile file : imageFiles) {
			logger.info("======> File name : " + file.getOriginalFilename());
			logger.info("======> File name : " + file.getSize());
			
			//썸네일
			String thumbPath = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			
			files.add(thumbPath);
		}
		vo.setFiles(files);

		
		service.regist(vo);
		model.addAttribute("result", "success");
		
		//board폴더 안에 success.jsp 파일이 만들어져야하므로. => /board/success로 하면 success화면에서 F5누르면 계속 같은 데이터 추가되기 때문에 redirect 시켜야 함.
		return "redirect:/sBoard/list";
	}
	
	
	//글 읽기
	@RequestMapping(value = "readPage", method = RequestMethod.GET)
	public void readPage(@RequestParam("bno") int bno, SearchCriteria cri, Model model) {
		logger.info("======> readPage ----- GET");
		
		BoardVO vo = service.read(bno);
		service.increaseViewCnt(bno);
		
		logger.info("BoardVO = " + vo);
		logger.info("cri = " + cri);
		logger.info("page = " + cri.getPage());
		 
		model.addAttribute("boardVO", vo);
		model.addAttribute("cri", cri);
		
	}
	
		
	//삭제
	@RequestMapping(value = "removePage", method = RequestMethod.POST)
	public String removePage(@RequestParam("bno") int bno, SearchCriteria cri, Model model) {
		logger.info("======> removePage ----- POST");
		logger.info("페이지번호 = " + cri.getPage());
		logger.info("검색종류 = " + cri.getSearchType());
		logger.info("검색어 = " + cri.getKeyword());
		logger.info("서버에서 지워야 할 파일 이름 = ");
		
		try {
			//삭제하고 싶은 파일 가지고 오기 => 삭제할 bno에 해당하는 파일들을 가지고 온다. 삭제할 파일이 몇개인지 모르니까 리스트로 받아온다.
			List<String> files = service.read(bno).getFiles();
			
			for(String delNames : files) {
				//썸네일
				File file = new File(uploadPath + delNames);
				file.delete();
				
				//원본
				String front = delNames.substring(0, 12);
				String end = delNames.substring(14);
				String originalFileName = front + end;
				
				File file2 = new File(uploadPath + originalFileName);
				file2.delete();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		service.remove(bno);
		
		// redirect 때는 cri 객체 전체를 전달할 수 없음. 하나씩 심어서 보내야함. ex) "page", cri.getPage()
		model.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/sBoard/list?page=" + cri.getPage() + "&searchType=" + cri.getSearchType();
	}
	
	
	//수정
	//글 수정할 때 수정해야 할 부분 받아오기
	@RequestMapping(value = "modifyPage", method = RequestMethod.GET)
	public void modifyPage(@RequestParam("bno") int bno, SearchCriteria cri, Model model) {
		logger.info("======> modifyPage ----- GET");
		
		BoardVO vo = service.read(bno);
			
		model.addAttribute("boardVO", vo);
		model.addAttribute("cri", cri);
	}
		
	//글 수정하고 나서 페이지 있는 목록으로 돌아가기
	@RequestMapping(value = "modifyPage", method = RequestMethod.POST)
	//input에 타입이 file 경우 multipartfile로 리턴됨.
	public String modifyPage(BoardVO vo, @RequestParam("bno") int bno, SearchCriteria cri, Model model, String[] delFiles, List<MultipartFile> addFiles) throws IOException {
		logger.info("======> modifyPage ----- POST");
		logger.info("bno = " + bno);
		logger.info("페이지번호 = " + cri.getPage());
		logger.info("검색종류 = " + cri.getSearchType());
		logger.info("검색어 = " + cri.getKeyword());
		
		//수정화면에서 X버튼 눌렀을 경우에만
		if(delFiles != null) {
			for(String delFile : delFiles) {
				logger.info("수정하려는 파일이름 =====> " + delFile);
				
				//썸네일
				File file = new File(uploadPath + delFile);
				file.delete();
				
				//원본
				String front = delFile.substring(0, 12);
				String end = delFile.substring(14);
				String originalFileName = front + end;
				
				File file2 = new File(uploadPath + originalFileName);
				file2.delete();
			}
		}

		//썸네일이 여러개 있을 수 있으므로
		List<String> addImages = new ArrayList<>();
		//수정 시 새로운 파일 등록하고 싶을 때
		for(MultipartFile file : addFiles) {
			logger.info("추가하려는 파일이름 =====> " + file.getOriginalFilename());
			logger.info("추가하려는 파일의 크기 =====> " + file.getSize());
			
			//추가하기(썸네일) => DB에 추가돼야 함.
			
			//파일크기가 0이상 일 때만(만약 수정화면에서 파일 추가안한다면?)
			if(file.getSize() > 0) {
				String thumb = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
				
				addImages.add(thumb);
			}	
		}
		
		service.modifyFile(vo, delFiles, addImages);
		
		// redirect 때는 cri 객체 전체를 전달할 수 없음. 하나씩 심어서 보내야함. ex) "page", cri.getPage()
		model.addAttribute("keyword", cri.getKeyword());

		return "redirect:/sBoard/readPage?page=" + cri.getPage() + "&bno=" + vo.getBno() + "&page=" + cri.getPage() + "&searchType=" + cri.getSearchType();
	}
	
	
	// 서버 <-> 브라우저(데이터 요청하면 보내줄거니까 안보여도 됨) 파일명에 해당하는 이미지의 데이터만 줌.
	@ResponseBody
	@RequestMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String filename){
		ResponseEntity<byte[]> entity = null;
		logger.info("DisplayFile = " + filename);
		
		try {
			//확장자에 따라서 미디어타입 결정(확장자만 가지고 온다.)
			String format = filename.substring(filename.lastIndexOf(".") + 1);	//확장자만 가지고 옴.
			MediaType mType = MediaUtils.getMediaType(format);	//맞는 미디어 타입 찾아냄.
			
			HttpHeaders headers = new HttpHeaders();
			InputStream in = null;
			in = new FileInputStream(uploadPath + "/" + filename);	//서버 파일 경로 C:/zzz/upload/~~~~.filename.jpg
			headers.setContentType(mType);	//고객 브라우저로 돌려주는 헤더에 미디어 타입 알려줌.
			
												//이미지 파일의 데이터
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
			
			in.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

	
}
