package net.hue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.BoardBean;
import net.hue.bean.ReplyBean;
import net.hue.dao.ProductQnADao;

public class Product_QnAViewController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int board_no=Integer.parseInt(request.getParameter("board_no"));
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		String state=request.getParameter("state");
		// state=view(내용보기)/edit(수정)/del(삭제)
		
		ProductQnADao bdao=new ProductQnADao();
		BoardBean bc=bdao.getBoardView(board_no);
		//번호에 해당하는 게시판 내용 불러오기
		String board_view=bc.getBoard_cont().replace("\n","<br>");
		//입력한 글내용 엔터키치면 줄바꿈되어서 나옴
		
		List<ReplyBean> replist=bdao.getReplyView(board_no,page);
		request.setAttribute("replist", replist);
		
		request.setAttribute("board_no", board_no);
		request.setAttribute("bc", bc);		
		request.setAttribute("board_view", board_view);
		request.setAttribute("page", page);
		
		ActionForward forward=new ActionForward();
        forward.setRedirect(false);
        
        if(state.equals("view")) {//내용보기 일때
        	forward.setPath("./noticeCenter/product_QnA_view.jsp");
        }else if(state.equals("reply")) {//답변글 폼일때
        	forward.setPath("./noticeCenter/product_QnA_reply.jsp");
        }else if(state.equals("edit")) {//수정폼 일때
        	forward.setPath("./noticeCenter/product_QnA_edit.jsp");
        }else if(state.equals("del")) {//삭제폼 일때
        	forward.setPath("./noticeCenter/product_QnA_del.jsp");
        }
        return forward;
	}
}
