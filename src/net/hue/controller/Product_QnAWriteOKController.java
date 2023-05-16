package net.hue.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.BoardBean;
import net.hue.dao.ProductQnADao;

public class Product_QnAWriteOKController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		String board_name=request.getParameter("board_name");
		String board_title=request.getParameter("board_title");
		String board_pwd=request.getParameter("board_pwd");
		String board_cont=request.getParameter("board_cont");
		
		BoardBean b=new BoardBean();
		b.setBoard_name(board_name); b.setBoard_title(board_title);
		b.setBoard_pwd(board_pwd); b.setBoard_cont(board_cont);
		
		ProductQnADao bdao=new ProductQnADao();
		bdao.insertProductQnA(b);//게시판 저장
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);//새로운 매핑주소로 이동
	    forward.setPath("noticeCenter_product_QnA.net");
		return forward;
	}

}
