package net.hue.controller;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import net.hue.bean.ReviewBean;
import net.hue.dao.ReviewDAO;

public class ReviewWriteOKController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		String saveFolder=request.getRealPath("upload");//이진파일 업로드 실제 서버 경로
		System.out.println(saveFolder);
		int fileSize=5*1024*1024;//이진파일 업로드 최대 크기 =>5메가바이트
		MultipartRequest multi=null;//cos.jar에 있는 파일 첨부 라이브러리 api
		
		multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
		ReviewBean r=new ReviewBean();
		
		
		String board_name=multi.getParameter("board_name");
		String board_title=multi.getParameter("board_title");
		String board_pwd=multi.getParameter("board_pwd");
		String board_cont=multi.getParameter("board_cont");
		
		File upFile=multi.getFile("board_file1");//첨부된 파일을 가져옴
		
		if(upFile != null) {//첨부된 파일이있는 경우
			String fileName=upFile.getName();//첨부된 파일명을 가져온다
			Calendar cal=Calendar.getInstance();
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;// 1월이 0으로 반영 되서 +1함
			int date=cal.get(Calendar.DATE);
			
			String homedir=saveFolder+"/"+year+"-"+month+"-"+date;//오늘 날짜 폴더 경로를 저장
			File path01=new File(homedir);
			if(!(path01.exists())) {//오늘 날짜 폴더 경로가 없다면 
				path01.mkdir();//오늘 날짜 폴더를 생성
			}
			
			Random d=new Random();
			int random=d.nextInt(100000000);
			
			int index=fileName.lastIndexOf(".");//첨부된 파일에서 .를 맨 오른쪽부터 찾아서 위치번호를 왼쪽부터 카운터 해서 구함
			String fileExtenSion=fileName.substring(index+1);//첨부파일 확장자를 구함
      		String refileName="board"+year+month+date+random+"."+fileExtenSion;//새로운 첨부파일명을 저장
      		String fileDBName="/"+year+"-"+month+"-"+date+"/"+refileName;//DB에 저장될 레코드 값
      		
      		upFile.renameTo(new File(homedir+"/"+refileName));//오늘 날짜 생성된 폴더 경로에 변경된 첨부파일로 실제 업로드
      		
      		r.setBoard_file1(fileDBName);
      		//r.setBoard_file2(fileDBName);
      		//r.setBoard_file3(fileDBName);
		}
		
		
		r.setBoard_name(board_name); r.setBoard_title(board_title);
		r.setBoard_pwd(board_pwd); r.setBoard_cont(board_cont);
		
		
		ReviewDAO bdao=new ReviewDAO();
	
		bdao.insertReview(r);//게시판저장
		
		//if(re==1) {
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);//새로운 매핑주소로 이동
	    forward.setPath("noticeCenter_review.net");
		return forward;
		//}
		
		//return null;
	
	}
	
}
