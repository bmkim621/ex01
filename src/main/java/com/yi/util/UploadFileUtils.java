package com.yi.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {

									//업로드 경로, 					파일 이름, 				파일 데이터
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws IOException {
		
		UUID uid = UUID.randomUUID(); // 중복되지 않는 고유한 키 값을 만든다.
		String savedName = uid.toString() + "_" + originalName;

		String savedPath = calcPath(uploadPath);
		
		File target = new File(uploadPath + savedPath + "/" + savedName); // 빈 파일만 만들어짐 C:/zzz/upload/2019/02/13/
		FileCopyUtils.copy(fileData, target); // 브라우저로부터 받은 데이터를 빈 파일에다가 복사함.
		
//		return savedPath + "/" + savedName;	//컨트롤러에서 메서드 사용할거기 때문에 savedName에 연/월/일 붙여서 보낸다.
			
		// ==============
		// Thumbnail 이미지
		// ==============
		String thumPath = makeThumbnail(uploadPath, savedPath, savedName);
		
		return thumPath;
	}
	
	
	//uploadPath = c:/zzz/upload, path : 2019/02/13, fileName : 원본 파일의 이름
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws IOException {
		
		//원본 이미지의 데이터를 가져와서 가상의 이미지를 만든다.
														//uploadPath + path 경로에 fileName 이름을 가진 파일을 만든다.
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
		//세로 100 크기에 고정하고, 가로 너비는 자동 조절하도록 하여 Thumbnail 데이터를 만든다.
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		//thumbnail 파일명을 만든다(앞에 s_가 붙는다.)
		String thumbnailName = uploadPath + path + "/" + "s_" + fileName;
		
		File newFile = new File(thumbnailName);	//빈 파일 만든다.
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		//만들어진 빈 thumbnail 파일에 destImg 데이터를 넣는다.
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		return path + "/" + "s_" + fileName;	//thumbnail용 파일 주소를 넘긴다.
	}
	
	//오늘날짜의 년/월/일 폴더 만들기
	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();	//Calendar -> 싱글톤 패턴으로 구현되어 있어서 메모리 효율적
		String yearPath = "/" + cal.get(Calendar.YEAR);
							//월 : 1 ~ 12까지니까 자릿수 맞춰주기 위해서 format 사용
		String monthPath = String.format("%s/%02d", yearPath, cal.get(Calendar.MONTH) + 1); //결과: 년/월
		String datePath = String.format("%s/%02d", monthPath, cal.get(Calendar.DATE));	// 결과: 년/월/일
		
		//2019/02/13
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		return datePath;	// 2019/02/13에 대한 정보를 가지고 있음.
	}
	
	//2019 폴더 -> 2019 폴더 안에 02(월) -> 02 폴더 안에 13(일)처럼 폴더 안에 폴더 만들기
	private static void makeDir(String uploadPath, String... paths) {
		
		for(String path : paths) {
			File dirPath = new File(uploadPath + path);
			
			if(dirPath.exists() == false) {
				dirPath.mkdir();
			}
		}
	}
}
