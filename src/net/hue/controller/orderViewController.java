package net.hue.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hue.bean.CartBean;
import net.hue.bean.MemberBean;
import net.hue.dao.CartDao;
import net.hue.dao.MemberDao;

public class orderViewController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		String memid = (String) session.getAttribute("memid");
		MemberDao mdao = MemberDao.getInstance();
		MemberBean mbean = mdao.getMemberById(memid);
		//int memno=Integer.parseInt(request.getParameter("memno"));
		
		String[] rowcheck = request.getParameterValues("rowcheck");
		CartDao ctdao = CartDao.getInstance();
		int ctno = -1;
		ArrayList<CartBean> ctlist = new ArrayList<CartBean>();
		int totalPrice = 0;
		
		String madedToTheOneRowcheck = "";
		
		
		if(rowcheck != null){ // 장바구니를 통해 들어왔을 경우
			for(String no: rowcheck){
				ctlist.add(ctdao.getItem(Integer.parseInt(no)));
			}
		
			//다음페이지로 넘길때 rowcheck를 한번 더 사용하기 위해 하나의 문자열로 만듬.
			if(rowcheck.length == 1){
				madedToTheOneRowcheck = rowcheck[0] + ",";	
			}
			else if(rowcheck.length > 1){
				madedToTheOneRowcheck = "" + rowcheck[0];
				for(int i = 1; i< rowcheck.length; i++){
					madedToTheOneRowcheck += "," + rowcheck[i];
				}
			}	
		}	
		else{ // 바로 구매하기 버튼을 눌러 들어온 경우
			ctno = Integer.parseInt(request.getParameter("ctno")); // 장바구니에 담긴 상품의 번호를 들고와야 함.
			ctlist.add(ctdao.getItem(ctno));
		}
		
		
		for(CartBean ctbean : ctlist){
			totalPrice += ctbean.getOneprice()*ctbean.getQty();
		}
		
		request.setAttribute("mbean", mbean);		
		request.setAttribute("ctdao", ctdao);
		request.setAttribute("rowcheck", rowcheck);
		request.setAttribute("ctlist", ctlist);
		request.setAttribute("madedToTheOneRowcheck", madedToTheOneRowcheck);
		request.setAttribute("ctno", ctno);
		request.setAttribute("totalPrice", totalPrice);
		
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./member/orderView.jsp");
		return forward;
	}

}
