package net.hue.controller;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hue.bean.CartBean;
import net.hue.bean.MemberBean;
import net.hue.bean.ProductBean;
import net.hue.bean.StockBean;
import net.hue.dao.CartDao;
import net.hue.dao.OrderDao;
import net.hue.dao.ProductDao;
import net.hue.dao.StockDao;

public class orderProcController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		String ctno = request.getParameter("ctno"); // 바로 결제하기를 통해 넘어왔을 경우만 null이 아님
		String rowcheck = request.getParameter("rowcheck"); // 장바구니 통해서 넘어온 경우만 null이 아님
		
		MemberBean mbean = new MemberBean();
		mbean.setNo((int)session.getAttribute("memno"));
		mbean.setName(request.getParameter("name"));
		mbean.setZip(request.getParameter("zip"));
		mbean.setAddr1(request.getParameter("addr1"));
		mbean.setAddr2(request.getParameter("addr2"));
		mbean.setMobile1(request.getParameter("mobile1"));
		mbean.setMobile2(request.getParameter("mobile2"));
		mbean.setMobile3(request.getParameter("mobile3"));
		
		StockDao sdao = StockDao.getInstance();
		CartDao ctdao = CartDao.getInstance();
		ProductDao pdao = ProductDao.getInstance();
		OrderDao odao = OrderDao.getInstance();
		
		
		/* 결제를 진짜로 진행시키기 전에 재고가 결제를 진행시키기에 충분한지 체크 */
		
		boolean isEnoughStcok = true;
		String msg = "";
		
		if(ctno.equals("-1") == true){ // 장바구니를 통해 넘어온 경우 => rowcheck를 모두 이용
			String[] rowcheckArr = rowcheck.split(",");
			for(String no : rowcheckArr){
				//장바구니에 담겨있는 상품의 pno, 옵션 이름, 주문수량을 얻음
				CartBean ctbean = ctdao.getItem(Integer.parseInt(no));
				int pno = ctbean.getPno();
				String opname = ctbean.getOpname();
				int qty = ctbean.getQty();
				
				//상품의 재고데이터를 가져와서 재고가 충분한지 체크함
				ArrayList<StockBean> stlist = sdao.getAllStockByPno(pno);
				for(StockBean stbean : stlist){
					if(opname.equals(stbean.getOpname())){
						ProductBean pbean = pdao.getProduct(pno);
						System.out.println("계산결과");
						System.out.println(stbean.getCount() - qty < 0);
						if(stbean.getCount() - qty < 0){ // (해당 상품의 재고 - 주문할 수량)
							isEnoughStcok = false;
							msg += pbean.getName() + "의 재고부족. ";
							break;
						}
					}
				}
				
				if(isEnoughStcok){
					break;
				}
			}
		}
		else{	// 바로 결제하기를 타고 넘어온 경우 => 1개의 상품이므로 ctno를 바로이용
			
			//장바구니에 담겨있는 상품의 pno, 옵션 이름, 주문수량을 얻음
			CartBean ctbean = ctdao.getItem(Integer.parseInt(ctno));
			int pno = ctbean.getPno();
			String opname = ctbean.getOpname();
			int qty = ctbean.getQty();
			
			//상품의 재고데이터를 가져와서 재고가 충분한지 체크함
			ArrayList<StockBean> stlist = sdao.getAllStockByPno(pno);
			for(StockBean stbean : stlist){
				if(opname.equals(stbean.getOpname())){
					ProductBean pbean = pdao.getProduct(pno);
					System.out.println("계산결과");
					System.out.println(stbean.getCount() - qty < 0);
					if(stbean.getCount() - qty < 0){ // (해당 상품의 재고 - 주문할 수량)
						isEnoughStcok = false;
						msg += pbean.getName() + "의 재고부족. ";
						break;
					}
				}
			}
		}
		
		if(isEnoughStcok){
			//결제를 진행하고, 장바구니에서 해당 상품들을 제거
			if(ctno.equals("-1") == true){ // 장바구니를 통해 넘어온 경우 => rowcheck를 모두 이용
				String[] rowcheckArr = rowcheck.split(","); //rowcheck 파싱
				for(String no : rowcheckArr){
					CartBean ctbean = ctdao.getItem(Integer.parseInt(no));
					odao.insertOrder(ctbean, mbean);
					sdao.updateByOrder(ctbean.getPno(), ctbean.getOpname(), ctbean.getQty());//해당 상품의 재고수량 update
				}
				
				// 주문이 완료된 상품은 장바구니에서 제거
				for(String no : rowcheckArr){
					ctdao.deleteItem((Integer.parseInt(no))); 
				}
				
				msg = "주문성공";
				
			}else{	// 바로 결제하기를 타고 넘어온 경우 => 1개의 상품이므로 ctno를 바로이용
				CartBean ctbean = ctdao.getItem(Integer.parseInt(ctno)); // 상품정보를 얻음.
				odao.insertOrder(ctbean, mbean);
				sdao.updateByOrder(ctbean.getPno(), ctbean.getOpname(), ctbean.getQty()); //해당 상품의 재고수량 update
				ctdao.deleteItem(Integer.parseInt(ctno));// 주문이 완료된 상품은 장바구니에서 제거
				
				msg = "주문성공";
			}
		}
		
		out.println("<script>");
		out.println("alert('"+msg+".');");
		out.println("location='member_main.net';");
		out.println("</script>");
	
		return null;
	}
	
}
