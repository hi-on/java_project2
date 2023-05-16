package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.BoardBean;
import net.hue.dao.ProductQnADao;
//게시판 수정 컨트롤러
public class Product_QnAEditController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		request.setCharacterEncoding("UTF-8");
		
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
			//get으로 전달된 쪽번호를 정수 숫자로 바꿔서 저장
		}
		
		int board_no=Integer.parseInt(request.getParameter("board_no"));
		
		String board_name=request.getParameter("board_name");
        String board_title=request.getParameter("board_title");
        String board_pwd=request.getParameter("board_pwd");
        String board_cont=request.getParameter("board_cont");
		//String board_date=request.getParameter("board_date").substring(0,10);
        ProductQnADao bdao=new ProductQnADao();
        
		BoardBean eb=new BoardBean();
    	eb.setBoard_no(board_no); eb.setBoard_name(board_name); eb.setBoard_title(board_title);
    	eb.setBoard_pwd(board_pwd); eb.setBoard_cont(board_cont);
    	
    	
    	int re=bdao.editProductQnA(eb);
    	BoardBean db_pwd=bdao.getBoardView(board_no);
        
        
        if(!db_pwd.getBoard_pwd().equals(board_pwd)) {
        	out.println("<script>");
        	out.println("alert('비번이 다릅니다!');");
        	out.println("history.back();");
        	out.println("</script>");
        }else {
        	
        	
        	if(re==1) {
        		ActionForward forward=new ActionForward();
                forward.setRedirect(true);
                forward.setPath("noticeCenter_product_QnA_view.net?board_no="+board_no+"&page="+page+"&state=view");
                return forward;
        	}
        }
			return null;	
	}

}
