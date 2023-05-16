<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:include page="../include/header.jsp" />
<script type="text/javascript">
function selectAll(selectAll)  {
  const checkboxes 
       = document.getElementsByName('cart_check');
  
  checkboxes.forEach((checkbox) => {
    checkbox.checked = selectAll.checked;
  });
}
</script>

<div class="-frame">
 <div class="contents">
<!-- 안내바 S -->
  <div id="expath" class="path">
   <ol>
    <li>
     <a href="../main/index.jsp">Home</a>
    </li>
    <li>
     <a href="cart.jsp"><strong>장바구니</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E --> 

<!-- 장바구니 리스트 본문 S -->
  <div class="d1-cart">
   <div class="listtopimg">
    <p class="banner"></p>
   </div>
   <div class="titleArea center">
    <h2>장바구니</h2>
   </div>

   <div class="delivery_list">
    <table id="local_delivery">
     <tr>
      <td>상품</td>
     </tr>
    </table>
 
    <table id="deli_list">
     <tr>
      <th><input type="checkbox" onclick="selectAll(this)" ></th>
      <th>이미지</th>
      <th>상품명</th>
      <th>판매가</th>
      <th>수량</th>
      <th>배송비</th>
      <th>합계</th>
      <th>선택</th>
     </tr>
  
     <tr class="deli_listcont">
      <td><input type="checkbox" name="cart_check" ></td>
      <td class="deli_img"><img src="../images/table.jpg"></td>
      <td class="left">테테라테테 테이블</td>
      <td>1200원</td>
      <td>5</td>
      <td>1000원</td>
      <td>1200*5+1000</td>
      <td class="button">
       <input class="deli_order" type="button" value="주문하기" onclick="">
       <input class="deli_del" type="button" value="삭제" onclick="">
      </td>
     </tr>
   
     <tr class="deli_listcont">
      <td><input type="checkbox" name="cart_check" ></td>
      <td class="deli_img"><img src="../images/tent.jpeg"></td>
      <td class="left">텐테라텐텐 텐트</td>
      <td>2000원</td>
      <td>3</td>
      <td>1000원</td>
      <td>2000*3+1000</td>
      <td class="button">
       <input class="deli_order" type="button" value="주문하기" onclick="">
       <input class="deli_del" type="button" value="삭제" onclick="">
      </td>
     </tr>
    </table>
 
    <div class="delete_btn">
     <input class="del_btn" type="button" value="선택 삭제" onclick="">
     <input class="del_btn" type="button" value="전체 삭제" onclick="">
     <input class="continue_btn" type="button" value="쇼핑 계속하기">
    </div>
 
    <table id="total_cartpay">
     <tr class="total_cartpaylist">
      <th>총 상품금액</th>
      <th>총 배송비</th>
      <th>결제 금액</th>
     </tr>
     <tr>
      <td>
       <div class="cart_text1">
        <strong><span class="cart_text1-1">6,000</span></strong>원
       </div>
      </td>
      <td>
       <div class="cart_text1">
        <strong><strong>+ </strong><span class="cart_text1-1">1,000</span></strong>원
       </div>
      </td>
      <td>
       <div class="cart_text2">
        <strong><strong>= </strong><span class="cart_text2-1">7,000</span></strong>원
       </div>
      </td>
    </table>
   </div>

   <div id="cart_btn">
    <input class="all_btn" type="button" value="전체 주문">
    <input class="choice_btn" type="button" value="선택 주문">
   </div>
<!-- 장바구니 리스트 본문 E -->

<!-- 장바구니 이용안내 S -->
   <div id="cart_Info">
    <div class="cartinfo_title">
     <h4>이용안내</h4>
    </div>

    <div class="cartinfo_cont">
     <p class="cartinfo_subtitle">장바구니 이용안내</p>
     <ul>
      <li>1) 해외배송 안됨</li>
      <li>2) 수량변경 가능</li>
      <li>3) 모르면 문의</li>
     </ul>
    </div>

    <div class="cartinfo_cont">
     <p class="cartinfo_subtitle">무이자할부 이용안내</p>
     <ul>
      <li>1) 결제하면 할부가능</li>
      <li>2) 3개월 무이자 가능</li>
      <li>3) 10만원 이상 결제 시 5개월 무이자 혜택</li>
     </ul>
    </div>
   </div>
  </div>
<!-- 장바구니 이용안내 E -->
 </div>
</div>


<jsp:include page="../include/footer.jsp" />