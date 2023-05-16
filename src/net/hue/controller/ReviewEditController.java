package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.ReviewBean;
import net.hue.dao.ReviewDAO;

public class ReviewEditController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		request.setCharacterEncoding("UTF-8");
		
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		int board_no=Integer.parseInt(request.getParameter("board_no"));
		
		String board_name=request.getParameter("board_name");
		String board_title=request.getParameter("board_title");
		String board_pwd=request.getParameter("board_pwd");
		String board_cont=request.getParameter("board_cont");
		
		ReviewDAO bdao=new ReviewDAO();
		
		ReviewBean db_pwd=bdao.getBoardView(board_no);	
		
		if(!db_pwd.getBoard_pwd().equals(board_pwd)) {
			out.println("<script>");
			out.println("alert('비번이 다릅니다!');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			ReviewBean eb=new ReviewBean();
			eb.setBoard_no(board_no); eb.setBoard_name(board_name);
			eb.setBoard_title(board_title); eb.setBoard_cont(board_cont);
			
			int re=bdao.editBoard(eb);
			
			if(re==1) {
				ActionForward forward=new ActionForward();
				forward.setRedirect(true);//수정,저장,삭제  이후에는 새로운 레코드값을 확인하기 위해서 
				//새로운 매핑주소 이동해야 한다.
				forward.setPath("noticeCenter_review_view.net?board_no="+board_no
						+"&page="+page+"&state=cont");
				return forward;
			}
		}
		
		return null;
	}

}
