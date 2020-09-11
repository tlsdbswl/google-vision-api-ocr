<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<html>
<head>
<meta charset="UTF-8">

<script>
	$(document)
			.on(
					'click',
					'#btnList',
					function(e) {
						e.preventDefault();
						location.href = "${pageContext.request.contextPath}/main.do";

					});
</script>
</head>

<body>

	<article>
		<div class="container" role="main">
			<h2>이미지 파일에서 텍스트 가져오기</h2>
			<form name="form" id="form" method="get"
				action="${pageContext.request.contextPath}/main.do">

				<div class="mb-3">
					<input type="text" class="form-control" name="businessRegNo"
						id="businessRegNo" placeholder="사업자 등록 번호 입력">
				</div>
			</form>

			<button type="submit" class="btn btn-sm btn-primary" id="btnSubmit"
				form="form">업로드</button>
			<button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>
			<div>
				${result}
			</div>
		</div>
	</article>

</body>

</html>


