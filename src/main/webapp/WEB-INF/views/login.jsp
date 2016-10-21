<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<title>Login Page</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/common.css">
</head>
<body onload='document.loginForm.username.focus();'>

	<div class="page-header">
		<h1>Key Manager App</h1>
	</div>

	<div id="login-box">
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		<form name='loginForm' action="<c:url value='/login' />" method='POST'>
			<%-- 			action="<c:url value='/j_spring_security_check' />" method='POST'> --%>
			<h3>Log In</h3>
			<!-- <div class="form-group">
        <input name="username" type="text" class="form-control input-lg" placeholder="Username" />
    </div> -->
			<div class="form-group">
				<input name="password" type="password" class="form-control input-lg"
					placeholder="Password" />
			</div>
			<div class="form-group">
				<button class="btn btn-primary btn-lg btn-block loginBtn"
					style="font-size: 20px; font-family: verdana; background-color: #007AFF"
					name="submit" type="submit">Submit</button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
	</div>

</body>
</html>