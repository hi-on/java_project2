package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.hue.dao.ProductDao;
import net.hue.dao.StockDao;

public class prodInsertProcController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		request.setCharacterEncoding("UTF-8");
		
		// 1. 웹서버 내에서 저장될 위치를 지정하기 위해, jsp의 내장객체 config를 이용해서 웹서버 내에서 특정 위치의 경로를 생성
		String targetLocation = request.getServletContext().getRealPath("/admin/product_images");
		
		// 2. 최대 업로드 가능 사이즈를 지정 및 인코딩 방식 지정
		int maxSize = 1024 * 1024 * 10; 	// 5메가
		String encType = "UTF-8";
		// 3. 웹서버 폴더 내의 targetLocation 경로로 업로드 실행(객체 생성 = 업로드 실행임)
		MultipartRequest multi = new MultipartRequest(request, targetLocation, maxSize, encType, new DefaultFileRenamePolicy());
		/* form에서 요청한 정보를 가져오는 방법(바로 위에서 만든 객체를 이용함, 파일 업로드를 위해 넘어온 경우에는 request 내장 객체로 접근 불가) */
		
		ProductDao pdao = ProductDao.getInstance();
		int cnt1 = pdao.insertProduct(multi);
		if(cnt1 > 0){
			System.out.println("상품 삽입 완료");
		}else{
			System.out.println("상품 삽입 실패");
		}
		
		StockDao sdao = StockDao.getInstance();
		int cnt2 = sdao.insertStock(multi);
		
		if(cnt2 > 0){
			System.out.println("재고 삽입 완료");
		}else{
			System.out.println("재고 삽입 실패");
		}
		out.println("<script>");
		out.println("alert('상품등록에 성공하였습니다.');");
		out.println("location='admin_product.net';");
		out.println("</script>");
		
		return null;
	}
}
