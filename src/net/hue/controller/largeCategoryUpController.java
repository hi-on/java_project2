package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.dao.CategoryDao;

public class largeCategoryUpController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int lno = Integer.parseInt(request.getParameter("lno"));
		int lstep = Integer.parseInt(request.getParameter("lstep")); // 해당 대분류의 순서
		
		CategoryDao cdao = CategoryDao.getInstance();	
		
		int result = -1;
		
		if (lstep > 1) { // 수정하려는 행의 step이 맨 첫번째가 아닌 경우
			result = cdao.updateUpLStep(lno, lstep);
		
			ActionForward forward=new ActionForward();
	  		forward.setRedirect(false);//새로운 매핑주소로 이동
	  	    forward.setPath("admin_category.net");//로그인 매핑주소로 이동
	  	    return forward;
		}else if(lstep == 1) {
			out.println("<script>");
			out.println("alert('대분류의 처음입니다.');");
			out.println("location='admin_category.net';");
			out.println("</script>");
		}
		return null;
	}
}
