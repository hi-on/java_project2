<%@ page contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="./css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<jsp:include page="../include/header.jsp" />
<script type="text/javascript">
function collapse(element) {
    var before = document.getElementsByClassName("active")[0]               // 기존에 활성화된 버튼
    if (before && document.getElementsByClassName("active")[0] != element) {  // 자신 이외에 이미 활성화된 버튼이 있으면
        before.nextElementSibling.style.maxHeight = null;   // 기존에 펼쳐진 내용 접고
        before.classList.remove("active");                  // 버튼 비활성화
    }
    element.classList.toggle("active");         // 활성화 여부 toggle

    var content = element.nextElementSibling;
    if (content.style.maxHeight != 0) {         // 버튼 다음 요소가 펼쳐져 있으면
        content.style.maxHeight = null;         // 접기
    } else {
        content.style.maxHeight = content.scrollHeight + "px";  // 접혀있는 경우 펼치기
    }
}
</script>

<div class="-frame">
 <div class="contents">
<!-- 안내바 S -->
  <div id="expath" class="path">
   <ol>
    <li>
     <a href="member_main.net">Home</a>
    </li>
    <li>
     <a href="noticeCenter_FAQ.net"><strong>FAQ</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->

  <div class="guideLink_title">FAQ</div>
   <div class="guideMenu">
    <ul class="menucategory">
     <li><a href="noticeCenter_notice.net">공지사항</a></li>
     <li><a href="noticeCenter_product_QnA.net">QnA</a></li>
     <li><a href="noticeCenter_FAQ.net"><strong style="color: #222;">FAQ</strong></a></li>  
     <li><a href="noticeCenter_review.net">포토후기</a></li>  
    </ul>
   </div>
   
<%-- 본문 --%>
 <div class="container">
  <h2>자주 묻는 질문</h2>
  <div class="accordion">
   <div class="accordion-item">
    <button id="accordion-button-1" aria-expanded="false" onclick="collapse(this);">
     <span class="accordion-title">
      <span style="color: #03b5d2">[결제/배송]</span> 운송방법(택배, 퀵)에 따라 어느 정도 시간이 걸리나요?
     </span>
     <span class="icon" aria-hidden="true"></span>
    </button>
    <div class="accordion-content">
     <p>
		배송시간은 운송방법에 따라 다소 차이가 있습니다.<br>
		택배의 경우 제품발송완료후 48시간 이내에 고객님께서 받으실 수 있습니다.<br>
		퀵서비스의 경우 제품준비완료후 당일수령하실 수 있으며 운임비는 착불입니다.
     </p>
    </div>
   </div>
   <div class="accordion-item">
    <button id="accordion-button-2" aria-expanded="false" onclick="collapse(this);">
     <span class="accordion-title">
      <span style="color: #03b5d2">[결제/배송]</span> 전화로 상품을 구입할 수 있나요?
     </span>
     <span class="icon" aria-hidden="true"></span>
    </button>
    <div class="accordion-content">
     <p>
		전화를 통한 구입문의는 대표전화 010-0000-0000 상담원과의 상담을 통해 하실 수 있습니다.
     </p>
    </div>
   </div>
   <div class="accordion-item">
    <button id="accordion-button-3" aria-expanded="false" onclick="collapse(this);">
     <span class="accordion-title">
      <span style="color: #03b5d2">[결제/배송]</span> 직접 방문하여 제품을 수령할 수 있나요?
     </span>
     <span class="icon" aria-hidden="true"></span>
    </button>
    <div class="accordion-content">
     <p>
		구매시 메시지에 방문수령을 기입하신후 판매장으로 방문하셔서 직접 제품을 수령하시면 됩니다.<br>(단 일요일, 공휴일은 제외)
		<br><br>
		[직접수령 상세안내]<br>
		- 주문상품의 준비는 결제완료를 기준으로 합니다.<br>
		- 배송지연상품 / 대량 제품의 주문은 준비 기간이 지연될 수 있으니 방문 전 담당자와 통화하여 상품 준비 여부를 반드시 확인하시기 바랍니다.
     </p>
    </div>
   </div>
   <div class="accordion-item">
    <button id="accordion-button-4" aria-expanded="false" onclick="collapse(this);">
     <span class="accordion-title">
      <span style="color: #03b5d2">	[회원가입/정보]</span> 회원가입을 해야만 상품구입이 가능한가요?
     </span>
     <span class="icon" aria-hidden="true"></span>
    </button>
    <div class="accordion-content">
     <p>
		휴캠핑마켓에서의 상품구입은 정회원, 비회원 모두 가능합니다.<br>
		단, 비회원의 경우에는 정회원에게 부여되는 적립금, 이벤트 등의 다양한 혜택을 받으실 수 없으므로 이점 참고하시기 바랍니다.
     </p>
    </div>
   </div>
   <div class="accordion-item">
    <button id="accordion-button-5" aria-expanded="false" onclick="collapse(this);">
     <span class="accordion-title">
      <span style="color: #03b5d2">[회원가입/정보]</span> 회원가입은 어떻게 하나요?
     </span>
     <span class="icon" aria-hidden="true"></span>
    </button>
    <div class="accordion-content">
     <p>
		화면 상단의 '회원가입'메뉴를 통해 누구나 무료로 회원가입을 할 수 있습니다.<br>
		회원가입은 아이디, 비밀번호, 간략한 신상내역을 기재하는 소정의 절차를 거쳐 진행됩니다.<br>
		<br>
		회원정보는 휴캠핑마켓 개인정보 보호정책에 따라 안전하게 보호되고 있으므로 안심하고 가입하실 수 있습니다.
     </p>
    </div>
   </div>
   <div class="accordion-item">
    <button id="accordion-button-6" aria-expanded="false" onclick="collapse(this);">
     <span class="accordion-title">
      <span style="color: #03b5d2">[회원가입/정보]</span> 아이디나 비밀번호를 분실했을 경우에는 어떻게 하나요?
     </span>
     <span class="icon" aria-hidden="true"></span>
    </button>
    <div class="accordion-content">
     <p>
		화면 상단의 '로그인'버튼을 클릭하신 후. 'ID/PW찾기' 버튼을 클릭하시면 확인할 수 있습니다.
     </p>
    </div>
   </div>
   <div class="accordion-item">
    <button id="accordion-button-7" aria-expanded="false" onclick="collapse(this);">
     <span class="accordion-title">
      <span style="color: #03b5d2">[교환/반품/환불]</span> 구입제품의 반품이 가능한가요?
     </span>
     <span class="icon" aria-hidden="true"></span>
    </button>
    <div class="accordion-content">
     <p>
		1. 반품 및 환불은 구입 후 10일 이내에만 가능하고 제품 상태가 완전해야 합니다.(박스, 내용품 포함)<br>
		2. 제품상태에 이상이 있을 시에는 판매가의 20%를 차감합니다.<br>
		3. 구입후 제품이 맘에 들지않아(변심) 반품하실 경우에는 제품의 포장이 뜯겨져 있으면 안됩니다.<br>
		&nbsp&nbsp&nbsp&nbsp보내는 운송비도 소비자께서 부담을 하셔야 합니다.
     </p>
    </div>
   </div>

  </div>
</div>
 
 </div>
</div>

<jsp:include page="../include/footer.jsp" />