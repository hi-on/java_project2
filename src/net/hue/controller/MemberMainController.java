package net.hue.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hue.bean.CategoryBean;
import net.hue.bean.MemberBean;
import net.hue.bean.ProductBean;
import net.hue.dao.CartDao;
import net.hue.dao.CategoryDao;
import net.hue.dao.MemberDao;
import net.hue.dao.ProductDao;

public class MemberMainController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
	
		ProductDao pdao = ProductDao.getInstance();
		ArrayList<ProductBean> list = pdao.getAllProduct();
	
		// 헤더 부분  오른쪽상단 메뉴바
		HttpSession session=request.getSession();
		String memid = (String)session.getAttribute("memid");
		
		MemberDao mdao = MemberDao.getInstance();
		MemberBean mbean = mdao.getMemberById(memid);
		
		if(memid != null) {
			int result = 0;
            if(session.getAttribute("memno") != null){
            int memno = (int)session.getAttribute("memno");
            CartDao ctdao = CartDao.getInstance();
            result = ctdao.countItemInCart(memno);	
            request.setAttribute("result", result);
            }
		}
		request.setAttribute("mbean", mbean);
		// 헤더 오른쪽 상단 메뉴 end
		
		// 헤더 카테고리
		CategoryDao cdao = CategoryDao.getInstance();	
		ArrayList<CategoryBean> clist = cdao.getAllCategory();

		request.setAttribute("clist", clist);
		// 헤데 카테고리 end
		request.setAttribute("list", list);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./main/index.jsp");
		return forward;
	}

}
