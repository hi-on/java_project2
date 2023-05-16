package net.hue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.hue.bean.ProductBean;
import net.hue.dao.ProductDao;

public class showLargeCategoryController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		//int lcno = Integer.parseInt(request.getParameter("lcno"));
		
		int lcno=0;
		if(request.getParameter("lcno") != null) {
		    try {
		        lcno = Integer.parseInt(request.getParameter("lcno"));
		    } catch (NumberFormatException e) {
		        e.printStackTrace();
		    }
		}
		
		ProductDao pdao = ProductDao.getInstance();

		
		/*     23.05.12  페이징       */
		
		int page=1;
		int limit=8;
		/*
		String find_field=null;//검색어 필드 
		String find_name=null;//검색어 
		*/
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		/*
		find_field=request.getParameter(find_field);
		
		if(request.getParameter("find_name") != null) {
			find_name=request.getParameter("find_name").trim();
		}
		if(request.getParameter("find_field") != null) {
			find_name=request.getParameter("find_field").trim();
		}
		*/
		ProductBean findR=new ProductBean();
		
		/*
		findR.setFind_field(find_field); 
		findR.setFind_name("%"+find_name+"%");
		*/
		
		int listcount=pdao.getLargeListCount(findR,lcno);
		List<ProductBean> plist=pdao.getLcList(page,limit,findR,lcno);
	
		int maxpage=(int)((double)listcount/limit+0.95);//총 페이지수
        int startpage=(((int)((double)page/8+0.875))-1)*8+1;//시작페이지
        int endpage=maxpage;//마지막 페이지
        if(endpage>startpage+8-1) endpage=startpage+8-1;
		
        request.setAttribute("plist",plist);//plist속성키이름에 목록을 저장
        request.setAttribute("page", page);//쪽번호 
        
        request.setAttribute("startpage",startpage);//시작페이지
        request.setAttribute("endpage",endpage);//마지막 페이지
        request.setAttribute("maxpage",maxpage);
        
        request.setAttribute("listcount",listcount);
        /*
        request.setAttribute("find_field",find_field);//검색필드가 저장
        request.setAttribute("find_name",find_name);//검색어
        */
        
        request.setAttribute("lcno",lcno);
		
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./member/showLargeCategory.jsp");
		return forward;
		
	}
}
