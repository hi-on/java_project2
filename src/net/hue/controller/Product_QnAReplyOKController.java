package net.hue.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.BoardBean;
import net.hue.bean.ReplyBean;
import net.hue.dao.ProductQnADao;

public class Product_QnAReplyOKController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		
		//책갈피 기능 페이지번호 히든값
		int page=Integer.parseInt(request.getParameter("page"));	
		/*if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
			//get으로 전달된 쪽번호를 정수 숫자로 바꿔서 저장
		}*/
		request.setAttribute("page",page);
		
		int board_no=Integer.parseInt(request.getParameter("board_no"));
		/*if(request.getParameter("board_no") != null) {
			board_no=Integer.parseInt(request.getParameter("board_no"));
			//get으로 전달된 쪽번호를 정수 숫자로 바꿔서 저장
		}*/
		request.setAttribute("board_no",board_no);
		
		
		String reply_title=request.getParameter("reply_title");
		String reply_name=request.getParameter("reply_name");		
		String reply_pwd=request.getParameter("reply_pwd");
		String reply_cont=request.getParameter("reply_cont");
		/*String reply_date=request.getParameter("reply_date");*/
		
		
		
		ReplyBean rb=new ReplyBean();
		rb.setBoard_no(board_no);
		rb.setReply_title(reply_title); rb.setReply_name(reply_name);
		rb.setReply_pwd(reply_pwd); rb.setReply_cont(reply_cont); 
		/*rb.setReply_date(reply_date);*/
		
		ProductQnADao bdao=new ProductQnADao();
		bdao.replyBoard(rb,board_no); //답변 저장
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);//새로운 매핑주소로 이동
	    forward.setPath("noticeCenter_product_QnA_view.net?board_no="+board_no+"&page="+page+"&state=view");
		return forward;
	}

}
