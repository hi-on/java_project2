package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReviewReplyController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		request.setCharacterEncoding("UTF-8");
		
		//책갈피 기능 페이지번호 히든값
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
			//get으로 전달된 쪽번호를 정수 숫자로 바꿔서 저장
		}
		request.setAttribute("page",page);
			
		int board_no=Integer.parseInt(request.getParameter("board_no"));
		
		request.setAttribute("board_no",board_no);
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/noticeCenter/review_reply.jsp?board_no="+board_no+"&page="+page+"&state=cont");
		return forward;
		
	}
}
