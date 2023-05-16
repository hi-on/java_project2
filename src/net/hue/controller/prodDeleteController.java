package net.hue.controller;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.ProductBean;
import net.hue.dao.ProductDao;
import net.hue.dao.StockDao;

public class prodDeleteController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int pno = Integer.parseInt(request.getParameter("pno"));

		ProductDao pdao = ProductDao.getInstance();
		ProductBean pbean = pdao.getProduct(pno);
		
		//웹서버 내에서 저장될 위치를 지정하기 위해, 웹서버 내에서 특정 위치의 경로를 생성
		String targetLocation = request.getServletContext().getRealPath("/admin/product_images"); 
		
		//메인 이미지가 null이 아니면
		if(pbean.getMainImgN() != null){
			File delFile = new File(targetLocation, pbean.getMainImgN());
			
			boolean isSuccessed = false;
			
			if(delFile.exists()){ // true, false
				
				isSuccessed = delFile.delete(); // 파일 삭제
			}
		}
		
		//서브이미지 1이 null이 아니면
		if(pbean.getDetailImgN1() != null){
			File delFile = new File(targetLocation, pbean.getDetailImgN1());
			
			boolean isSuccessed = false;
			
			if(delFile.exists()){ // true, false
				
				isSuccessed = delFile.delete(); // 파일 삭제
			}
		}
		
		//서브이미지 2이 null이 아니면
		if(pbean.getDetailImgN2() != null){
			File delFile = new File(targetLocation, pbean.getDetailImgN2());
			
			boolean isSuccessed = false;
			
			if(delFile.exists()){ // true, false
				
				isSuccessed = delFile.delete(); // 파일 삭제
			}	
		}
		
		//서브이미지 3이 null이 아니면
		if(pbean.getDetailImgN3() != null){
			File delFile = new File(targetLocation, pbean.getDetailImgN3());
			
			boolean isSuccessed = false;
			
			if(delFile.exists()){ // true, false
				
				isSuccessed = delFile.delete(); // 파일 삭제
			}	
		}
		
		//서브이미지 4이 null이 아니면
		if(pbean.getDetailImgN4() != null){
			File delFile = new File(targetLocation, pbean.getDetailImgN4());
			
			boolean isSuccessed = false;
			
			if(delFile.exists()){ // true, false
				
				isSuccessed = delFile.delete(); // 파일 삭제
			}	
		}
				
		/* DB에서 해당 행 지우기 */
		//cnt값은 사실
		int cnt = pdao.deleteProduct(pno);
		
		if(cnt > 0) {
			StockDao sdao = StockDao.getInstance();
			/* 해당하는 상품번호에 사이즈테이블 데이터를 삭제 한다. */
			int cnt2 = sdao.deleteAllStock(pno);
			out.println("<script>");
			out.println("alert('상품삭제가 완료되었습니다.');");
			out.println("location='admin_product.net';");
			out.println("</script>");
		}else {
			out.println("<script>");
		out.println("alert('상품삭제가 실패되었습니다.');");
		out.println("location='admin_product.net';");
		out.println("</script>");
		
		}
		
		return null;
	}
}
