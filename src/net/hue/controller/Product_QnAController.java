package net.hue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.BoardBean;
import net.hue.dao.ProductQnADao;

public class Product_QnAController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int page=1;
		int limit=10;
		
		String find_field=null; //검색 필드
		String find_name=null;//검색어
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		
		
		if(request.getParameter("find_field") != null) {
			find_field=request.getParameter("find_field").trim();
		}
		if(request.getParameter("find_name") != null) {
			find_name=request.getParameter("find_name").trim();
		}
		
		BoardBean findB=new BoardBean();
		findB.setFind_field(find_field);
		findB.setFind_name("%"+find_name+"%");
		
		ProductQnADao bdao=new ProductQnADao();
		
		int listcount=bdao.getListCount(findB);
		List<BoardBean> blist=bdao.getBoardList(page,limit,findB);
				
		//페이지 연산
		int maxpage=(int)((double)listcount/limit+0.95);//총 페이지수
        int startpage=(((int)((double)page/10+0.9))-1)*10+1;//시작페이지
        int endpage=maxpage;//마지막 페이지
        if(endpage>startpage+10-1) endpage=startpage+10-1;
        
        request.setAttribute("blist",blist);//blist속성키이름에 목록을 저장
        request.setAttribute("page", page);//쪽번호 
        request.setAttribute("startpage",startpage);//시작페이지
        request.setAttribute("endpage",endpage);//마지막 페이지
        request.setAttribute("maxpage",maxpage);
        request.setAttribute("listcount",listcount);
        request.setAttribute("find_field",find_field);//board_title,board_cont 검색필드가 저장
        request.setAttribute("find_name", find_name);
		
        
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./noticeCenter/product_QnA.jsp");
		return forward;
	}

}
