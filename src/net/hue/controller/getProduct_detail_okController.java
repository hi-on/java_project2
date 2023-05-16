package net.hue.controller;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.ProductBean;
import net.hue.bean.StockBean;
import net.hue.dao.ProductDao;
import net.hue.dao.StockDao;


public class getProduct_detail_okController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();  // 출력 스트림 생성
		
		ProductDao pdao = ProductDao.getInstance();
		ProductBean pbean = new ProductBean();
		
		int pno = Integer.parseInt(request.getParameter("no"));
		StockDao sdao = StockDao.getInstance();
		ArrayList<StockBean> slist = sdao.getAllStockByPno(pno);
		
		ActionForward forward=new ActionForward();
		
		request.setCharacterEncoding("UTF-8");
		
		int no = Integer.parseInt(request.getParameter("no"));
		pbean.setNo(no);
		
		ProductBean prod = pdao.getProduct(no);
		
		if(prod == null) {
			out.println("<script>");
			out.println("alert('상품정보를 찾을 수 없습니다!');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			request.setAttribute("prodNo", prod.getNo());
			request.setAttribute("prodName", prod.getName());
			request.setAttribute("prodInfo", prod.getInfo());
			request.setAttribute("prodOriPri", prod.getOriprice());
			request.setAttribute("prodDisPri", prod.getDiscprice());
			request.setAttribute("prodMainImg", prod.getMainImgN());
			request.setAttribute("prodDetailImg1", prod.getDetailImgN1());
			request.setAttribute("prodDetailImg2", prod.getDetailImgN2());
			request.setAttribute("prodDetailImg3", prod.getDetailImgN3());
			request.setAttribute("prodDetailImg4", prod.getDetailImgN4());
			request.setAttribute("Lc", prod.getLcname());
			request.setAttribute("Sc", prod.getScname());
			forward.setRedirect(false);
			forward.setPath("./member/productDetail.jsp");
			
			request.setAttribute("slist", slist);
			
			return forward;
		}
		
		return null;

	}

}
