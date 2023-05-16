package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.dao.CategoryDao;

public class smallCategoryUpController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int lno = Integer.parseInt(request.getParameter("lno"));
		int sno = Integer.parseInt(request.getParameter("sno"));
		int sstep = Integer.parseInt(request.getParameter("sstep")); // 해당 대분류의 순서
		
	 	CategoryDao cdao = CategoryDao.getInstance();	

	 		
		int result = -1;
		
		if (sstep > 1) { // 수정하려는 행의 step이 맨 첫번째가 아닌 경우
			result = cdao.updateUpSStep(lno, sno, sstep);
			
			ActionForward forward=new ActionForward();
	  		forward.setRedirect(false);//새로운 매핑주소로 이동
	  	    forward.setPath("admin_category.net");//로그인 매핑주소로 이동
	  	    return forward;
		}else if(sstep == 1) {
			out.println("<script>");
			out.println("alert('소분류의 처음입니다.');");
			out.println("location='admin_category.net';");
			out.println("</script>");
		}
		return null;
	}
}
