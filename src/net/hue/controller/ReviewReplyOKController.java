package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.ReviewReplyBean;
import net.hue.dao.ReviewDAO;

public class ReviewReplyOKController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		
		//책갈피 기능 페이지번호 히든값
		int page=Integer.parseInt(request.getParameter("page"));	

		request.setAttribute("page",page);
		
		int board_no=Integer.parseInt(request.getParameter("board_no"));

		request.setAttribute("board_no",board_no);
		
		
		String reply_title=request.getParameter("reply_title");
		String reply_name=request.getParameter("reply_name");		
		String reply_pwd=request.getParameter("reply_pwd");
		String reply_cont=request.getParameter("reply_cont");

		
		ReviewReplyBean rb=new ReviewReplyBean();
		rb.setBoard_no(board_no);
		rb.setReply_title(reply_title); rb.setReply_name(reply_name);
		rb.setReply_pwd(reply_pwd); rb.setReply_cont(reply_cont); 
		
		
		ReviewDAO bdao=new ReviewDAO();
		bdao.replyBoard(rb,board_no); //답변 저장
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);//새로운 매핑주소로 이동
	    forward.setPath("noticeCenter_review_view.net?board_no="+board_no+"&page="+page+"&state=cont");
		return forward;
	}

}
