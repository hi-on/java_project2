package net.hue.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.oreilly.servlet.MultipartRequest;

import net.hue.bean.ProductBean;

public class ProductDao {
	private Connection conn = null;
	
	private static ProductDao instance;
	public static ProductDao getInstance() {
		if(instance == null) {
			instance = new ProductDao();
		}
		return instance;
	}
	
	private ProductDao(){
		try {
			/* Context.xml을 살펴봐서 설정을 읽어본다 */
			Context initContext = new InitialContext();
			/* 내가 설정한 Context.xml 정보가 comp/env 이런 폴더안에 저장됨 */
			Context envContext = (Context) initContext.lookup("java:comp/env"); // java:comp/env 에 설정 정보가 저장되는건 내가 임의로 수정할 수 없음.
			/* 위 폴더가서 jbdc/OracleDb 이름으로 설정한 것을가져와라 */
			DataSource ds = (DataSource) envContext.lookup("jdbc/OracleDB");
			// 사용자가 사이트에 접속하면 컨넥션 객체를 얻음. 그리고 이 컨넥션 객체를 가지고 로그인을 하고 자시고 하는거임. 등등의 DB작업
			conn = ds.getConnection(); // 설정 정보를 가지고 계정에 접송해서 Connection 
			System.out.println("생성자에서 conn :" + conn);
		} catch (NamingException e) {
			System.out.println("CategoryDao 생성자에서 컨넥션 객체를 얻다가 오류 발생");
		} catch (SQLException e) {
			System.out.println("CategoryDao 생성자에서 컨넥션 객체를 얻다가 오류 발생");
		}
	}
	
	// 전체 상품 리스트
	public ArrayList<ProductBean> getAllProduct() {
		
		ArrayList<ProductBean> list = new ArrayList<ProductBean>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// 3. SQL 작상 및 분석
			String sql = "select pro.NO, pro.LCNAME, scate.name as SCNAME, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 \r\n"
					+ "from smallcategory scate inner join (select pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4\r\n"
					+ "from bigcategory lcate inner join product pro\r\n"
					+ "on lcate.no = pro.lcno) pro\r\n"
					+ "on scate.no = pro.scno order by no asc";
			ps = conn.prepareStatement(sql);
			
			// 4. SQL문 실행
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ProductBean pbean = new ProductBean();
				
				pbean.setNo(rs.getInt("no"));
				pbean.setLcname(rs.getString("lcname"));
				pbean.setScname(rs.getString("scname"));
				pbean.setName(rs.getString("name"));				
				pbean.setOriprice(rs.getInt("oriprice"));				
				pbean.setDiscprice(rs.getInt("discprice"));
				pbean.setInfo(rs.getString("info"));
				pbean.setMainImgN(rs.getString("mainImgN"));				
				pbean.setDetailImgN1(rs.getString("detailImgN1"));
				pbean.setDetailImgN2(rs.getString("detailImgN2"));
				pbean.setDetailImgN3(rs.getString("detailImgN3"));
				pbean.setDetailImgN4(rs.getString("detailImgN4"));
				
				list.add(pbean);
			}

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("getAllProduct() SQL문 실행중 오류 발생");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("접속 종료 실패");
			}
		}
		return list;
	}// getAllProduct()
	
	
	//검색 전후 목록 개수
	public int getListCount(ProductBean findR) {
		int count=0;
		
		PreparedStatement pt = null;
		ResultSet rs=null;
		try {
		
			String sql="select count(no) from product ";
			if(findR.getFind_field()==null) {
				//검색 전 개수(검색 필드 내용 없을 때)
				sql+="";
			}else if(findR.getFind_field().equals("name")) {
				sql+=" where name like ?";//글제목이랑 같을 때
			}
			
			pt=conn.prepareStatement(sql);
			
			if(findR.getFind_field()!=null) {
				//검색필드 내용 있을 때
				pt.setString(1, findR.getFind_name());
			}
			
			rs=pt.executeQuery();//쿼리문 수행 후 레코드 저장
			
			if(rs.next()) {
				count=rs.getInt(1);
			}
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close();
				if(pt != null) pt.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return count;
	}

	//목록
	public List<ProductBean> getProdList(int page, int limit, ProductBean findR) {
		List<ProductBean> plist=new ArrayList<>();
		PreparedStatement pt = null;
		ResultSet rs=null;
		
		try {
	         String sql = "select * from (select rowNum rNum, pro.NO, pro.LCNAME, scate.name as SCNAME, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 "
	               + "from smallcategory scate inner join (select rowNum rNum, pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 "
	               + "from bigcategory lcate inner join product pro"
	               + " on lcate.no = pro.lcno) pro"
	               + " on scate.no = pro.scno ";

	         if(findR.getFind_field() == null) {//검색전
	            sql+=" ";
	         }else if(findR.getFind_field().equals("name")) {
	            sql+=" where pro.name like ?";
	         }
	         sql+=" order by pro.NO asc) where rNum>=? and rNum<=?";
	         /* 페이징과 검색관련 쿼리문,  rowNum컬럼은 오라클에서 테이블 생성시 추가되는 컬럼으로 최초 레코드 저장
	          * 시 일련 번호값이 알아서 저장된다. rNum은 rowNum 컬럼의 별칭명이다.
	          */
			
			pt=conn.prepareStatement(sql);
			
			int startrow=(page-1)*10+1;//읽기 시작할 행번호. 10이 한페이지 보여지는 목록개수
			int endrow=startrow+limit-1;//읽을 마지막 행번호
			
			if(findR.getFind_field() != null) {//검색하는 경우
				pt.setString(1,findR.getFind_name());
				pt.setInt(2,startrow);
				pt.setInt(3,endrow);
			}else {//검색하지 않는 경우
				pt.setInt(1,startrow);
				pt.setInt(2,endrow);
			}
			
			rs=pt.executeQuery();
			
			while(rs.next()) {//복수개의 레코드가 검색되는 경우는  while반복문으로 반복
				ProductBean p=new ProductBean();
				p.setNo(rs.getInt("no"));
				p.setLcname(rs.getString("lcname"));
				p.setScname(rs.getString("scname"));
				p.setName(rs.getString("name"));
				p.setOriprice(rs.getInt("oriprice"));
				p.setDiscprice(rs.getInt("discprice"));
				p.setInfo(rs.getString("info"));
				p.setMainImgN(rs.getString("mainimgN"));
				p.setDetailImgN1(rs.getString("detailImgN1"));
				p.setDetailImgN2(rs.getString("detailImgN2"));
				p.setDetailImgN3(rs.getString("detailImgN3"));
				p.setDetailImgN4(rs.getString("detailImgN4"));
				
				
				plist.add(p);//컬렉션에 추가
			}
			
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close();
				if(pt != null) pt.close();				
			}catch(Exception e) {e.printStackTrace();}
		}
		return plist;
	}
	
	// 금주 아이템
		public ArrayList<ProductBean> getAllProduct1() {
			
			ArrayList<ProductBean> list = new ArrayList<ProductBean>();
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				// 3. SQL 작상 및 분석
				String sql = "select pro.NO, pro.LCNAME, scate.name as SCNAME, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 \r\n"
						+ "from smallcategory scate inner join (select pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4\r\n"
						+ "from bigcategory lcate inner join product pro\r\n"
						+ "on lcate.no = pro.lcno) pro\r\n"
						+ "on scate.no = pro.scno order by no desc";
				ps = conn.prepareStatement(sql);
				
				// 4. SQL문 실행
				rs = ps.executeQuery();
				
				while(rs.next()) {
					ProductBean pbean = new ProductBean();
					
					pbean.setNo(rs.getInt("no"));
					pbean.setLcname(rs.getString("lcname"));
					pbean.setScname(rs.getString("scname"));
					pbean.setName(rs.getString("name"));				
					pbean.setOriprice(rs.getInt("oriprice"));				
					pbean.setDiscprice(rs.getInt("discprice"));
					pbean.setInfo(rs.getString("info"));
					pbean.setMainImgN(rs.getString("mainImgN"));				
					pbean.setDetailImgN1(rs.getString("detailImgN1"));
					pbean.setDetailImgN2(rs.getString("detailImgN2"));
					pbean.setDetailImgN3(rs.getString("detailImgN3"));
					pbean.setDetailImgN4(rs.getString("detailImgN4"));
					
					list.add(pbean);
				}

			} catch (SQLException e) {
				System.out.println(e);
				System.out.println("getAllProduct() SQL문 실행중 오류 발생");
			} finally {
				try {
					if (ps != null)
						ps.close();
				} catch (SQLException e) {
					System.out.println("접속 종료 실패");
				}
			}
			return list;
		}// getAllProduct()
		
	// 대분류 상품리스트
	public ArrayList<ProductBean> getAllProductByLcno(int lcno){
		ArrayList<ProductBean> list = new ArrayList<ProductBean>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// 3. SQL 작상 및 분석
			String sql = "select pro.NO, pro.LCNAME, scate.name as scname, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 \r\n"
					+ "from smallcategory scate inner join \r\n"
					+ "(select pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4\r\n"
					+ "from bigcategory lcate inner join product pro\r\n"
					+ "on lcate.no = pro.lcno where lcate.no=? ) pro\r\n"
					+ "on scate.no = pro.scno";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, lcno);
			
			// 4. SQL문 실행
			rs = ps.executeQuery();
			System.out.println("여기까지 잘 옴");
			
			while(rs.next()) {
				ProductBean pbean = new ProductBean();
				
				pbean.setNo(rs.getInt("no"));
				pbean.setLcname(rs.getString("lcname"));
				pbean.setScname(rs.getString("scname"));
				pbean.setName(rs.getString("name"));				
				pbean.setOriprice(rs.getInt("oriprice"));				
				pbean.setDiscprice(rs.getInt("discprice"));
				pbean.setInfo(rs.getString("info"));
				pbean.setMainImgN(rs.getString("mainImgN"));				
				pbean.setDetailImgN1(rs.getString("detailImgN1"));
				pbean.setDetailImgN2(rs.getString("detailImgN2"));
				pbean.setDetailImgN3(rs.getString("detailImgN3"));
				pbean.setDetailImgN4(rs.getString("detailImgN4"));
				
				list.add(pbean);
			}

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("getAllProductByLcno() SQL문 실행중 오류 발생");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("접속 종료 실패");
			}
		}
		return list;
	}
	
	//대분류의 속한 소분류 리스트
	public ArrayList<ProductBean> getAllProductByScno(int scno){
		ArrayList<ProductBean> list = new ArrayList<ProductBean>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// 3. SQL 작상 및 분석
			String sql = "select pro.NO, pro.LCNAME, scate.name as scname, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 \r\n"
					+ "from smallcategory scate inner join \r\n"
					+ "(select pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4\r\n"
					+ "from bigcategory lcate inner join product pro\r\n"
					+ "on lcate.no = pro.lcno) pro\r\n"
					+ "on scate.no = pro.scno where scate.no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, scno);
			
			// 4. SQL문 실행
			rs = ps.executeQuery();
			System.out.println("여기까지 잘 옴");
			
			while(rs.next()) {
				ProductBean pbean = new ProductBean();
				
				pbean.setNo(rs.getInt("no"));
				pbean.setLcname(rs.getString("lcname"));
				pbean.setScname(rs.getString("scname"));
				pbean.setName(rs.getString("name"));				
				pbean.setOriprice(rs.getInt("oriprice"));				
				pbean.setDiscprice(rs.getInt("discprice"));
				pbean.setInfo(rs.getString("info"));
				pbean.setMainImgN(rs.getString("mainImgN"));				
				pbean.setDetailImgN1(rs.getString("detailImgN1"));
				pbean.setDetailImgN2(rs.getString("detailImgN2"));
				pbean.setDetailImgN3(rs.getString("detailImgN3"));
				pbean.setDetailImgN4(rs.getString("detailImgN4"));
				
				list.add(pbean);
			}

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("getAllProductByScno() SQL문 실행중 오류 발생");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("접속 종료 실패");
			}
		}
		return list;
	}
	
	//상품 추가
	public int insertProduct(MultipartRequest multi) {
		
		int cnt = -1;

		PreparedStatement ps = null;

		try {
			// 3. SQL 작상 및 분석
			String sql = "insert into product values(prodseq.nextval,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, Integer.parseInt(multi.getParameter("selLargeCategory")));
			ps.setInt(2, Integer.parseInt(multi.getParameter("selSmallCategory")));
			ps.setString(3, multi.getParameter("name"));
			ps.setInt(4, Integer.parseInt(multi.getParameter("oriprice")));
			ps.setInt(5, Integer.parseInt(multi.getParameter("discprice")));
			ps.setString(6, multi.getParameter("info"));
			ps.setString(7, multi.getFilesystemName("mainImg"));
			ps.setString(8, multi.getFilesystemName("detailImg1"));
			ps.setString(9, multi.getFilesystemName("detailImg2"));
			ps.setString(10, multi.getFilesystemName("detailImg3"));
			ps.setString(11, multi.getFilesystemName("detailImg4"));
			
			// 4. SQL문 실행
			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("insertProduct() SQL문 실행중 오류 발생");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("접속 종료 실패");
			}
		}
		return cnt;
	}
	
	//식별값에 대한 상품 정보
	public ProductBean getProduct(int no) {
		
		ProductBean pbean = null;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 3. SQL 작상 및 분석
			String sql = "select pro.NO, pro.LCNAME, scate.name as scname, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 \r\n"
					+ "from smallcategory scate inner join \r\n"
					+ "(select pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4\r\n"
					+ "from bigcategory lcate inner join product pro\r\n"
					+ "on lcate.no = pro.lcno where pro.no=?) pro\r\n"
					+ "on scate.no = pro.scno";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			// 4. SQL문 실행
			rs = ps.executeQuery();

			if (rs.next()) {
				pbean = new ProductBean();

				pbean.setNo(rs.getInt("no"));
				pbean.setLcname(rs.getString("lcname"));
				pbean.setScname(rs.getString("scname"));
				pbean.setName(rs.getString("name"));
				pbean.setOriprice(rs.getInt("oriprice"));
				pbean.setDiscprice(rs.getInt("discprice"));
				pbean.setInfo(rs.getString("info"));
				pbean.setMainImgN(rs.getString("mainImgN"));
				pbean.setDetailImgN1(rs.getString("detailImgN1"));
				pbean.setDetailImgN2(rs.getString("detailImgN2"));
				pbean.setDetailImgN3(rs.getString("detailImgN3"));
				pbean.setDetailImgN4(rs.getString("detailImgN4"));
			}

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("getProduct() SQL문 실행중 오류 발생");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("접속 종료 실패");
			}
		}
		return pbean;
	}
	
	//상품 삭제
	public int deleteProduct(int pno) {
		
		int cnt = -1;

		PreparedStatement ps = null;

		try {
			// 3. SQL 작상 및 분석
			String sql = "delete from product where no=" + pno;
			ps = conn.prepareStatement(sql);
			
			// 4. SQL문 실행
			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("deleteProduct() SQL문 실행중 오류 발생");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("접속 종료 실패");
			}
		}
		return cnt;
	}
	
	//상품업데이트
	public int updateProduct(MultipartRequest multi) {
		int cnt = -1;

		PreparedStatement ps = null;

		try {
			// 3. SQL 작상 및 분석
			String sql = "update product set lcno=?, scno=?, name=?, oriprice=?, discprice=?, info=?, mainimgn=?, detailimgn1=?, detailimgn2=?, detailimgn3=?, detailimgn4=? where no=?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, Integer.parseInt(multi.getParameter("selLargeCategory")));
			ps.setInt(2, Integer.parseInt(multi.getParameter("selSmallCategory")));
			ps.setString(3, multi.getParameter("name"));
			ps.setInt(4, Integer.parseInt(multi.getParameter("oriprice")));
			ps.setInt(5, Integer.parseInt(multi.getParameter("discprice")));
			ps.setString(6, multi.getParameter("info"));
			
			if(multi.getFilesystemName("mainImg") == null) {
				ps.setString(7, multi.getParameter("beforeMainImg"));
			}else {
				ps.setString(7, multi.getFilesystemName("mainImg"));
			}
			
			if(multi.getFilesystemName("detailImg1") == null) {
				ps.setString(8, multi.getParameter("beforeImg1"));
			}else {
				ps.setString(8, multi.getFilesystemName("detailImg1"));
			}
			
			if(multi.getFilesystemName("detailImg2") == null) {
				ps.setString(9, multi.getParameter("beforeImg2"));
			}else {
				ps.setString(9, multi.getFilesystemName("detailImg2"));
			}
			
			if(multi.getFilesystemName("detailImg3") == null) {
				ps.setString(10, multi.getParameter("beforeImg3"));
			}else {
				ps.setString(10, multi.getFilesystemName("detailImg3"));
			}
			
			if(multi.getFilesystemName("detailImg3") == null) {
				ps.setString(11, multi.getParameter("beforeImg4"));
			}else {
				ps.setString(11, multi.getFilesystemName("detailImg4"));
			}
			
			ps.setInt(12, Integer.parseInt(multi.getParameter("pno")));

			// 4. SQL문 실행
			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("updateProduct() SQL문 실행중 오류 발생");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("접속 종료 실패");
			}
		}
		return cnt;
	}
	
/*
	public ArrayList<ProductBean> getAllProductByName(String search){
		
		ArrayList<ProductBean> list = new ArrayList<ProductBean>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// 3. SQL 작상 및 분석
			String sql = "select * from\r\n"
					+ "(select pro.NO, pro.LCNAME, scate.name as scname, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 \r\n"
					+ "from scategory scate inner join \r\n"
					+ "(select pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4\r\n"
					+ "from lcategory lcate inner join product pro\r\n"
					+ "on lcate.no = pro.lcno) pro\r\n"
					+ "on scate.no = pro.scno)\r\n"
					+ "where upper(lcname) like ? or upper(scname) like ? or upper(name) like ?";
			ps = conn.prepareStatement(sql);
			
			search = search.toUpperCase(); // 대문자로 변환
			
			ps.setString(1, "%" + search + "%");
			ps.setString(2, "%" + search + "%");
			ps.setString(3, "%" + search + "%");
			
			// 4. SQL문 실행
			rs = ps.executeQuery();
			System.out.println("여기까지 잘 옴");
			
			while(rs.next()) {
				ProductBean pbean = new ProductBean();
				
				pbean.setNo(rs.getInt("no"));
				pbean.setLcname(rs.getString("lcname"));
				pbean.setScname(rs.getString("scname"));
				pbean.setName(rs.getString("name"));				
				pbean.setOriprice(rs.getInt("oriprice"));				
				pbean.setDiscprice(rs.getInt("discprice"));
				pbean.setInfo(rs.getString("info"));
				pbean.setMainImgN(rs.getString("mainImgN"));				
				pbean.setDetailImgN1(rs.getString("detailImgN1"));
				pbean.setDetailImgN2(rs.getString("detailImgN2"));
				pbean.setDetailImgN3(rs.getString("detailImgN3"));
				pbean.setDetailImgN4(rs.getString("detailImgN4"));
				
				list.add(pbean);
			}

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("getAllProductByName() SQL문 실행중 오류 발생");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("접속 종료 실패");
			}
		}
		return list;
	}
	*/
	
	// 안내바 대분류 상품리스트
	public ProductBean getProductByLcno(ProductBean pb){
		ProductBean ipb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// 3. SQL 작상 및 분석
			String sql = 
					"select pro.LCNAME, scate.name as scname "
					+ "from smallcategory scate inner join (select pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME\r\n"
					+ "from bigcategory lcate inner join product pro\r\n"
					+ "on lcate.no = pro.lcno) pro\r\n"
					+ "on scate.no = pro.scno where pro.NO='"+pb.getNo()+"'";
			ps = conn.prepareStatement(sql);
			
			// 4. SQL문 실행
			rs = ps.executeQuery();
			System.out.println("여기까지 잘 옴");
			
			if(rs.next()) {
				ipb = new ProductBean();
				ipb.setLcname(rs.getString("lcname"));
				ipb.setScname(rs.getString("scname"));
			}

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("getProductByLcno() SQL문 실행중 오류 발생");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("접속 종료 실패");
			}
		}
		return ipb;
	}

	/*23.05.12  대분류 페이징 */
public List<ProductBean> getLcList(int page, int limit, ProductBean findR, int lcno) {
		List<ProductBean> plist=new ArrayList<>();
		PreparedStatement pt=null;
		ResultSet rs=null;
		
		try {
	         String sql = "select rowNum rNum, pro.NO, pro.LCNAME, scate.name as SCNAME, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 "
	               + "from smallcategory scate inner join (select rowNum rNum, pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 "
	               + "from bigcategory lcate inner join product pro"
	               + " on lcate.no = pro.lcno where lcate.no=? ) pro"
	               + " on scate.no = pro.scno";

	
	         if(findR.getFind_field() == null) {//검색전
	            sql+="";
	         }else if(findR.getFind_field().equals("name")) {
	            sql+=" where name like ?";
	         }
	         sql+=" and rNum>=? and rNum<=?";
	         /* 페이징과 검색관련 쿼리문,  rowNum컬럼은 오라클에서 테이블 생성시 추가되는 컬럼으로 최초 레코드 저장
	          * 시 일련 번호값이 알아서 저장된다. rNum은 rowNum 컬럼의 별칭명이다.
	          */
			
	         pt=conn.prepareStatement(sql);
	         pt.setInt(1, lcno);
	         
	         int startrow=(page-1)*8+1;
	         int endrow=startrow+limit-1;
	         
	         if(findR.getFind_field() != null) {
	        	 pt.setString(2,findR.getFind_name());
	        	 pt.setInt(3,startrow);
	        	 pt.setInt(4,endrow);
	         }else {
	        	 pt.setInt(2, startrow);
	        	 pt.setInt(3,endrow);
	         }
	         
	         rs=pt.executeQuery();
	         
	         while(rs.next()) {
	        	 ProductBean pbean=new ProductBean();
	        	 
	        	 	pbean.setNo(rs.getInt("no"));
					pbean.setLcname(rs.getString("lcname"));
					pbean.setScname(rs.getString("scname"));
					pbean.setName(rs.getString("name"));
					pbean.setOriprice(rs.getInt("oriprice"));
					pbean.setDiscprice(rs.getInt("discprice"));
					pbean.setInfo(rs.getString("info"));
					pbean.setMainImgN(rs.getString("mainImgN"));
					pbean.setDetailImgN1(rs.getString("detailImgN1"));
					pbean.setDetailImgN2(rs.getString("detailImgN2"));
					pbean.setDetailImgN3(rs.getString("detailImgN3"));
					pbean.setDetailImgN4(rs.getString("detailImgN4"));
					plist.add(pbean);
	         }
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close();
				if(pt != null) pt.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return plist;
	}

//대분류 검색목록
	public int getLargeListCount(ProductBean findR,int lcno) {
		
		int count=0;
		
		PreparedStatement pt = null;
		ResultSet rs=null;
		try {
		
			String sql="select count(no) from product where lcno=? ";
			
			/*
			if(findR.getFind_field()==null) {
				//검색 전 개수(검색 필드 내용 없을 때)
				sql+="";
			}else if(findR.getFind_field().equals("name")) {
				sql=" where name like ?";//글제목이랑 같을 때
			}
			*/
			
			
			pt=conn.prepareStatement(sql);
			pt.setInt(1, lcno);
			/*
			if(findR.getFind_field()!=null) {
				//검색필드 내용 있을 때
				pt.setString(1, findR.getFind_name());
			}
			*/
			
			rs=pt.executeQuery();//쿼리문 수행 후 레코드 저장
								
			if(rs.next()) {
				count=rs.getInt(1);
			}
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close();
				if(pt != null) pt.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return count;
	}
	
	
	//05.15 소분류 페이징
		public List<ProductBean> getScList(int page, int limit, ProductBean findR, int scno, int lcno) {
			
			List<ProductBean> pslist=new ArrayList<>();
		      PreparedStatement pt=null;
		      ResultSet rs=null;
		      
		      try {
		            String sql = "select * from (select rowNum rNum, pro.NO, pro.LCNAME, scate.name as SCNAME, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 "
		                  + "from smallcategory scate inner join (select rowNum rNum, pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 "
		                  + "from bigcategory lcate inner join product pro"
		                  + " on lcate.no = pro.lcno where lcate.no=?) pro"
		                  + " on scate.no = pro.scno where scate.no=?";
		   
		            if(findR.getFind_field() == null) {//검색전
		               sql+="";
		            }else if(findR.getFind_field().equals("name")) {
		               sql+=" where name like ?";
		            }
		            sql+=" ) where rNum>=? and rNum<=?";
		            /* 페이징과 검색관련 쿼리문,  rowNum컬럼은 오라클에서 테이블 생성시 추가되는 컬럼으로 최초 레코드 저장
		             * 시 일련 번호값이 알아서 저장된다. rNum은 rowNum 컬럼의 별칭명이다.
		             */
		         
		            pt=conn.prepareStatement(sql);
		            pt.setInt(1, lcno);
		            pt.setInt(2, scno);  
		            
		            int startrow=(page-1)*8+1;
		            int endrow=startrow+limit-1;
		            
		            if(findR.getFind_field() != null) {
		               pt.setString(3,findR.getFind_name());
		               pt.setInt(4,startrow);
		               pt.setInt(5,endrow);
		            }else {
		               pt.setInt(3, startrow);
		               pt.setInt(4,endrow);
		            }
		            
		            rs=pt.executeQuery();
		            
		            while(rs.next()) {
		               ProductBean pbean=new ProductBean();
		               
		               pbean.setNo(rs.getInt("no"));
		               pbean.setLcname(rs.getString("lcname"));
		               pbean.setScname(rs.getString("scname"));
		               pbean.setName(rs.getString("name"));
		               pbean.setOriprice(rs.getInt("oriprice"));
		               pbean.setDiscprice(rs.getInt("discprice"));
		               pbean.setInfo(rs.getString("info"));
		               pbean.setMainImgN(rs.getString("mainImgN"));
		               pbean.setDetailImgN1(rs.getString("detailImgN1"));
		               pbean.setDetailImgN2(rs.getString("detailImgN2"));
		               pbean.setDetailImgN3(rs.getString("detailImgN3"));
		               pbean.setDetailImgN4(rs.getString("detailImgN4"));
		               pslist.add(pbean);
		            }
		      }catch(Exception e) {e.printStackTrace();}
		      finally {
		         try {
		            if(rs != null) rs.close();
		            if(pt != null) pt.close();
		         }catch(Exception e) {e.printStackTrace();}
		      }
		      return pslist;
		   }

	//소분류 검색목록
		public int getSmallListCount(ProductBean findR,int scno,int lcno) {
			
			int count=0;
			
			PreparedStatement pt = null;
			ResultSet rs=null;
			try {
			
				String sql="select count(no) from product where scno=? and lcno=? ";
				
				/*
				if(findR.getFind_field()==null) {
					//검색 전 개수(검색 필드 내용 없을 때)
					sql+="";
				}else if(findR.getFind_field().equals("name")) {
					sql=" where name like ?";//글제목이랑 같을 때
				}
				*/
				pt=conn.prepareStatement(sql);
				pt.setInt(1,scno);
				pt.setInt(2,lcno);
				/*
				if(findR.getFind_field()!=null) {
					//검색필드 내용 있을 때
					pt.setString(1, findR.getFind_name());
				}
				*/
				
				rs=pt.executeQuery();//쿼리문 수행 후 레코드 저장
				
				if(rs.next()) {
					count=rs.getInt(1);
				}
			}catch(Exception e) {e.printStackTrace();}
			finally {
				try {
					if(rs != null) rs.close();
					if(pt != null) pt.close();
				}catch(Exception e) {e.printStackTrace();}
			}
			return count;
		}

}
