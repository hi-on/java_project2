package net.hue.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.hue.bean.CartBean;
import net.hue.bean.MemberBean;
import net.hue.bean.OrderBean;
import net.hue.bean.ProductBean;

public class OrderDao {
	private Connection conn = null;

	private static OrderDao instance;

	public static OrderDao getInstance() {
		if (instance == null) {
			instance = new OrderDao();
		}
		return instance;
	}

	private OrderDao(){
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
	
	public ArrayList<OrderBean> getAllOrder(){
		
		ArrayList<OrderBean> list = new ArrayList<OrderBean>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 3. SQL 작상 및 분석
			String sql = "select o.no, o.id, o.pno, p.name, p.mainimgn, o.opname, o.qty, o.price, to_char(o.time, 'YYYY-MM-DD HH24:MI:SS') as time, o.receiver, o.rv_hp1, o.rv_hp2, o.rv_hp3, o.rv_zip, o.rv_addr1, o.rv_addr2 \r\n"
					+ "from product p inner join (select o.no, m.id, o.pno, o.opname, o.qty, o.price, o.time, o.receiver, o.rv_hp1, o.rv_hp2, o.rv_hp3, o.rv_zip, o.rv_addr1, o.rv_addr2 \r\n"
					+ "from member m inner join orderlist o\r\n"
					+ "on m.no = o.mno) o\r\n"
					+ "on p.no = o.pno order by o.no asc";
			ps = conn.prepareStatement(sql);

			// 4. SQL문 실행
			rs = ps.executeQuery();

			while(rs.next()) {
				OrderBean obean = new OrderBean();
				
				obean.setNo(rs.getInt("no"));
				obean.setId(rs.getString("id"));
				obean.setName(rs.getString("name"));
				obean.setPno(rs.getInt("pno"));
				obean.setMainimgn(rs.getString("mainimgn"));
				obean.setOpname(rs.getString("opname"));
				obean.setQty(rs.getInt("qty"));
				obean.setPrice(rs.getInt("price"));
				obean.setTime(rs.getString("time"));
				obean.setReceiver(rs.getString("receiver"));
				obean.setRv_hp1(rs.getString("rv_hp1"));
				obean.setRv_hp2(rs.getString("rv_hp2"));
				obean.setRv_hp3(rs.getString("rv_hp3"));
				obean.setRv_zip(rs.getString("rv_zip"));
				obean.setRv_addr1(rs.getString("rv_addr1"));
				obean.setRv_addr2(rs.getString("rv_addr2"));
				
				list.add(obean);
			}
			
		} catch (SQLException e) {
			System.out.println("getAllOrder() 실행중 에러");
			System.out.println(e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return list;
	}
	
	
	public int getListCount(OrderBean findR) {
		int count=0;
		
		PreparedStatement pt = null;
		ResultSet rs=null;
		try {
		
			String sql="select count(o.no) from orderlist o,"
					+ " product p where o.pno=p.no";
			if(findR.getFind_field()==null) {
				//검색 전 개수(검색 필드 내용 없을 때)
				sql+="";
			}else if(findR.getFind_field().equals("name")) {
				sql+=" and name like ?";//상품명이랑 같을 때
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

	public List<OrderBean> getOrderList(int page, int limit, OrderBean findR) {
		List<OrderBean> olist=new ArrayList<>();
		PreparedStatement pt = null;
		ResultSet rs=null;
		
		try {
	         String sql = 
	         "select * from (select rowNum rNum, o.no, o.id, o.pno, p.name, p.mainimgn, o.opname, o.qty, o.price, to_char(o.time, 'YYYY-MM-DD HH24:MI:SS') as time, o.receiver, o.rv_hp1, o.rv_hp2, o.rv_hp3, o.rv_zip, o.rv_addr1, o.rv_addr2 "
	      		+ "from product p inner join (select rowNum rNum, o.no, m.id, o.pno, o.opname, o.qty, o.price, o.time, o.receiver, o.rv_hp1, o.rv_hp2, o.rv_hp3, o.rv_zip, o.rv_addr1, o.rv_addr2 "
	      		+ "from member m inner join orderlist o"
	      		+ " on m.no = o.mno) o"
	      		+ " on p.no = o.pno";
	        		 

	         if(findR.getFind_field() == null) {//검색전
	            sql+=" ";
	         }else if(findR.getFind_field().equals("name")) {
	            sql+=" where name like ?"; //상품명이랑 같을 때
	         }
	         sql+=") where rNum>=? and rNum<=?";
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
				OrderBean o=new OrderBean();
				o.setNo(rs.getInt("no"));
				o.setId(rs.getString("id"));
				o.setName(rs.getString("name"));
				o.setPno(rs.getInt("pno"));
				o.setMainimgn(rs.getString("mainimgn"));
				o.setOpname(rs.getString("opname"));
				o.setQty(rs.getInt("qty"));
				o.setPrice(rs.getInt("price"));
				o.setTime(rs.getString("time"));
				o.setReceiver(rs.getString("receiver"));
				o.setRv_hp1(rs.getString("rv_hp1"));
				o.setRv_hp2(rs.getString("rv_hp2"));
				o.setRv_hp3(rs.getString("rv_hp3"));
				o.setRv_zip(rs.getString("rv_zip"));
				o.setRv_addr1(rs.getString("rv_addr1"));
				o.setRv_addr2(rs.getString("rv_addr2"));
				
				
				olist.add(o);//컬렉션에 추가
			}
			
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close();
				if(pt != null) pt.close();				
			}catch(Exception e) {e.printStackTrace();}
		}
		return olist;
	}
	
	
	public ArrayList<OrderBean> getAllOrderById(String id){
		
		ArrayList<OrderBean> list = new ArrayList<OrderBean>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 3. SQL 작상 및 분석
			String sql = "select o.no, o.id, p.name, p.mainimgn, o.opname, o.qty, o.price, to_char(o.time, 'YYYY-MM-DD HH24:MI:SS') as time, o.receiver, o.rv_hp1, o.rv_hp2, o.rv_hp3, o.rv_zip, o.rv_addr1, o.rv_addr2, \r\n"
					+ "from product p inner join (select o.no, m.id, o.pno, o.opname, o.qty, o.price, o.time, o.receiver, o.rv_hp1, o.rv_hp2, o.rv_hp3, o.rv_zip, o.rv_addr1, o.rv_addr2, \r\n"
					+ "from member m inner join orderlist o\r\n"
					+ "on m.no = o.mno) o\r\n"
					+ "on p.no = o.pno where id=?";
			ps = conn.prepareStatement(sql);

			ps.setString(1, id);
			
			// 4. SQL문 실행
			rs = ps.executeQuery();

			while(rs.next()) {
				OrderBean obean = new OrderBean();
				
				obean.setNo(rs.getInt("no"));
				obean.setId(rs.getString("id"));
				obean.setName(rs.getString("name"));
				obean.setMainimgn(rs.getString("mainimgn"));
				obean.setOpname(rs.getString("opname"));
				obean.setQty(rs.getInt("qty"));
				obean.setPrice(rs.getInt("price"));
				obean.setTime(rs.getString("time"));
				obean.setReceiver(rs.getString("receiver"));
				obean.setRv_hp1(rs.getString("rv_hp1"));
				obean.setRv_hp2(rs.getString("rv_hp2"));
				obean.setRv_hp3(rs.getString("rv_hp3"));
				obean.setRv_zip(rs.getString("rv_zip"));
				obean.setRv_addr1(rs.getString("rv_addr1"));
				obean.setRv_addr2(rs.getString("rv_addr2"));
				
				list.add(obean);
			}
			
		} catch (SQLException e) {
			System.out.println("getAllOrderById() 실행중 에러");
			System.out.println(e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return list;
	}
	
	public int insertOrder(CartBean ctbean, MemberBean mbean) {
		int cnt = -1;

		PreparedStatement ps = null;

		try {
			// 3. SQL 작상 및 분석
			String sql = "insert into orderlist values(orderseq.nextval,?,?,?,?,?,sysdate,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mbean.getNo());
			ps.setInt(2, ctbean.getPno());
			ps.setString(3, ctbean.getOpname());
			ps.setInt(4, ctbean.getQty());
			ps.setInt(5, ctbean.getOneprice());
			ps.setString(6, mbean.getName());
			ps.setString(7, mbean.getMobile1());
			ps.setString(8, mbean.getMobile2());
			ps.setString(9, mbean.getMobile3());
			ps.setString(10, mbean.getZip());
			ps.setString(11, mbean.getAddr1());
			ps.setString(12, mbean.getAddr2());

			// 4. SQL문 실행
			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("insertOrder() 실행중 에러");
			System.out.println(e);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return cnt;
	}

	//개인 주문정보 확인
	public ArrayList<OrderBean> getOrder(String mid) {
ArrayList<OrderBean> list = new ArrayList<OrderBean>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 3. SQL 작상 및 분석
			String sql = "select o.no, o.id, p.name, o.pno, p.mainimgn, o.opname, o.qty, o.price, to_char(o.time, 'YYYY-MM-DD HH24:MI:SS') as time, o.receiver, o.rv_hp1, o.rv_hp2, o.rv_hp3, o.rv_zip, o.rv_addr1, o.rv_addr2 \r\n"
					+ "from product p inner join (select o.no, m.id, o.pno, o.opname, o.qty, o.price, o.time, o.receiver, o.rv_hp1, o.rv_hp2, o.rv_hp3, o.rv_zip, o.rv_addr1, o.rv_addr2 \r\n"
					+ "from member m inner join orderlist o\r\n"
					+ "on m.no = o.mno) o\r\n"
					+ "on p.no = o.pno where id=?";
			ps = conn.prepareStatement(sql);

			ps.setString(1,mid);

			// 4. SQL문 실행
			rs = ps.executeQuery();

			while(rs.next()) {
				OrderBean obean = new OrderBean();
				
				obean.setNo(rs.getInt("no"));
				obean.setId(rs.getString("id"));
				obean.setName(rs.getString("name"));
				obean.setPno(rs.getInt("pno"));
				obean.setMainimgn(rs.getString("mainimgn"));
				obean.setOpname(rs.getString("opname"));
				obean.setQty(rs.getInt("qty"));
				obean.setPrice(rs.getInt("price"));
				obean.setTime(rs.getString("time"));
				obean.setReceiver(rs.getString("receiver"));
				obean.setRv_hp1(rs.getString("rv_hp1"));
				obean.setRv_hp2(rs.getString("rv_hp2"));
				obean.setRv_hp3(rs.getString("rv_hp3"));
				obean.setRv_zip(rs.getString("rv_zip"));
				obean.setRv_addr1(rs.getString("rv_addr1"));
				obean.setRv_addr2(rs.getString("rv_addr2"));
				
				list.add(obean);
			}
			
		} catch (SQLException e) {
			System.out.println("getAllOrder() 실행중 에러");
			System.out.println(e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return list;
	}

	public int deleteorder(int pno) {
		int cnt = -1;
		
		PreparedStatement ps = null;

		try {
			// 3. SQL 작상 및 분석
			String sql = "delete from orderlist where pno=" + pno;
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

	
}