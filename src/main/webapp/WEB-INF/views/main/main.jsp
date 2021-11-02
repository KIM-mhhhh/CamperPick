<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		var fname ="";
		var today =new Date();
		var hh = today.getHours();
		
		if(hh>=4 && hh<9){			//새벽 4시부터 낮 9시
			fname="${pageContext.request.contextPath}/resources/images/main_bg1.jpg";
		}else if(hh>=9 && hh<18){	//낮 9시부터 오후 6시
			fname="${pageContext.request.contextPath}/resources/images/main_bg2.jpg";
		}else if(hh>=18 && hh<24){	//오후 6시부터 밤 12시
			fname="${pageContext.request.contextPath}/resources/images/main_bg3.jpg";
		}else{						//밤 12시 부터 새벽 4시
			fname="${pageContext.request.contextPath}/resources/images/main_bg0.jpg";
		}
		
		$('.main_bg').css('background-image','url('+fname+')');
	});
</script>
<!-- 메인 시작 -->
<div id="main-content">
	<div class="main_bg">
		<div class="search_container">
    		<h1 class="search_title">
		        CAMPERPICK
		    </h1>
		    <div class="msearch_content">
		    	<form id="search_form" action="../camping/list.do">
		    		<input class="search_input" type="search" size="30" id="keyword" name="keyword" placeholder="지역 검색">
		    		<input class="search_btn" type="submit" value="검색하기">
		    	</form>
		    </div>
		</div>
	</div>
	
</div>
<div class="page-main">
<p style="height:100px;">
	<c:if test="${count==0 }">
		<div class="result-display">
			등록된 캠핑장이 없습니다
		</div>
	</c:if>
		<c:if test="${count>0 }">
		<div class="align-center">
				<input type="button" value="캠핑장 더보기"  class="button" onclick="location.href='${pageContext.request.contextPath}/camping/list.do'">
		
			<c:forEach var="camping" items="${list }">
				<Div class="horizontal-area">
					<a href="${pageContext.request.contextPath }/camping/detail.do?camping_num=${camping.camping_num}" >
						<c:if test="${!empty camping.filename }">
							<img src="${pageContext.request.contextPath }/camping/imageView.do?camping_num=${camping.camping_num}">
						</c:if>
						<c:if test="${empty camping.filename }">
							<img src="${pageContext.request.contextPath }/resources/images/noImage.gif">
						</c:if>
						<span>${camping.camp_name }</span>
					</a>
				</Div>
			</c:forEach>
		</div>
		
	</c:if>
	<div style="clear:both;">
	<p>
	</div>
	</div>	
<!-- 메인 끝 -->