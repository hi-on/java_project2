package net.hue.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.BoardBean;
import net.hue.bean.ReplyBean;
import net.hue.dao.ProductQnADao;

public class Product_QnAReplyController implements Action {

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
		//if(request.getParameter("board_no") != null) {
			//board_no=Integer.parseInt(request.getParameter("board_no"));
			//get으로 전달된 쪽번호를 정수 숫자로 바꿔서 저장
		//}
		request.setAttribute("board_no",board_no);
		
		//답변글 히든값
		//int reply_ref=Integer.parseInt(request.getParameter("reply_ref"));
		//int reply_step=Integer.parseInt(request.getParameter("reply_step"));
		//int reply_level=Integer.parseInt(request.getParameter("reply_level"));
			
		//String reply_no=request.getParameter("reply_no");
		
		
		
		
		//rb.setReply_ref(reply_ref); //rb.setReply_step(reply_step);
		//rb.setReply_level(reply_level);
		
				
		
		//out.println("<script>");
        //out.println("alert('답변 저장에 성공했습니다!');");
        //out.println("noticeCenter_product_QnA_view.net?board_no=board_no"+"&page="+page+"&state=view");
        //out.println("</script>");
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/noticeCenter/product_QnA_reply.jsp?board_no="+board_no+"&page="+page+"&state=view");
		return forward;
	}

}
