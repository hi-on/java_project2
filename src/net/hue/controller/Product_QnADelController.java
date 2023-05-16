package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.BoardBean;
import net.hue.dao.ProductQnADao;

public class Product_QnADelController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		
		int board_no=Integer.parseInt(request.getParameter("board_no"));
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		String board_pwd=request.getParameter("del_pwd");
		ProductQnADao bdao=new ProductQnADao();
		BoardBean db_pwd=bdao.getBoardView(board_no);
		
		if(!db_pwd.getBoard_pwd().equals(board_pwd)) {
			out.println("<script>");
        	out.println("alert('비번이 다릅니다!');");
        	out.println("history.go(-1);");
        	out.println("</script>");
		}else {
			bdao.delBoard(board_no);
			
			ActionForward forward=new ActionForward();
        	forward.setRedirect(true);
        	forward.setPath("noticeCenter_product_QnA.net?page="+page);
        	return forward;
		}
		return null;
	}

}
