<%@page import="net.hue.dao.StockDao"%>
<%@page import="net.hue.dao.ProductDao"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="net.hue.bean.CategoryBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.hue.dao.CategoryDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%  /* 파일 업로드 */
	// 1. 웹서버 내에서 저장될 위치를 지정하기 위해, jsp의 내장객체 config를 이용해서 웹서버 내에서 특정 위치의 경로를 생성
	String targetLocation = config.getServletContext().getRealPath("/admin/product_images");
	//System.out.println(targetLocation);
	
	// 2. 최대 업로드 가능 사이즈를 지정 및 인코딩 방식 지정
	int maxSize = 1024 * 1024 * 10; 	// 5메가
	String encType = "UTF-8"; 		// 한글이 안깨지도록
	
	// 3. 웹서버 폴더 내의 targetLocation 경로로 업로드 실행(객체 생성 = 업로드 실행임) 
	//MultipartRequest multi = new MultipartRequest(request, targetLocation, maxSize, encType);
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

%>

<script>
	location.href="prodList.jsp";
</script>
