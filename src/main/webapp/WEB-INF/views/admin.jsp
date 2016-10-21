<!DOCTYPE html>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<title>Welcome Key Manager App</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/common.css" />
<style type="text/css">
</style>

<script type="text/javascript">
	$(document).ready(function() {
		if ($('#successMsgBox').text() != "") {
			$('#successMsgBox').delay(1000).fadeOut(3000);
		}

		if ($('#errMsgBox').text() != "") {
		}

	});

	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}
</script>

</head>
<body>
	<c:set var="activeUsers" value="0" />
	<c:forEach items="${users}" var="user">
		<c:if test="${user.isAuthenticated()}">
			<c:set var="activeUsers" value="${activeUsers+1}"></c:set>
		</c:if>
	</c:forEach>
	<sec:authorize access="hasRole('ROLE_USER')">
		<%-- 		<c:url value="/j_spring_security_logout" var="logoutUrl" /> --%>
		<c:url value="/logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<div style="float: right; padding: 20px">
				<h3>
					<b>${pageContext.request.userPrincipal.name}</b> | <a
						href="javascript:formSubmit()"> Logout</a>
				</h3>
			</div>
		</c:if>

		<div class="container">
			<h3>Admin Page</h3>
			<div class="alert alert-info">
				<strong>NOTE:</strong> &nbsp;&nbsp;&nbsp;la la la la la la
			</div>
			<c:if test="${not empty error}">
				<c:choose>
					<c:when test="${error}">
						<div id="errMsgBox" class="alert alert-danger">
							<strong>Error!</strong> ${msg}.
						</div>
					</c:when>
					<c:otherwise>
						<div id="successMsgBox" class="alert alert-success"
							style="display:;">
							<strong>Success!</strong> ${msg}.
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<div class="tabs" style="width: 100%;">

				<ul class="nav nav-tabs">
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li class="tab active" id="tab2"><a href="#"><b>Manage
									Manage Users , Total IDP Users : <span class="badge">${users.size()}</span>
									Authorized Clients Users: <span class="badge">
										${activeUsers} </span>
							</b></a></li>
					</sec:authorize>
				</ul>

				<div class="tab-content" id="tab2-content" style="">
					<fieldset>
						<form id="myform"
							action="${pageContext.request.contextPath}/api/updateKeys"
							method="post">
							<table class="tab2 table">
								<tr>
									<th>User Name</th>
									<th>Endpoint Url</th>
									<th width="10%">Authorized</th>
								</tr>

								<tr>
									<td colspan="3">
										<div class="dataCellDiv">
											<table class="dataCell table table-hover">
												<c:forEach items="${users}" var="user">
													<input type="hidden" name="allIdpUsers"
														value="${user.userName}">
													<tr class="ele_rows" id="ele_row_${user.userName}">
														<td style="width: 20%">&nbsp;&nbsp;${user.userName}</td>
														<td style="width: 70%"><input type="text"
															class="form-control" name="endpoint_${user.userName}"
															value="${user.endpoint}"></td>
														<td style="width: 10%;" class="chkbox"><input
															id="chk_${user.userName}" type="checkbox"
															value="${user.userName}" name="selectedUsers"
															style="width: 20px; height: 20px; margin-right: 40px;"
															<c:if test="${user.isAuthenticated()}" >checked="checked"</c:if> /></td>
													</tr>
												</c:forEach>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<!--############ Spring security required for POST a form data ################-->
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
									<!--############ Spring security required for POST a form data ################-->
									<th colspan="3"
										style="text-align: center; border-radius: 10px;"><input
										class="btn btn-primary btn-lg btn-block loginBtn"
										type="submit" value="Submit"
										style="border-radius: 5px; width: auto;" /></th>
								</tr>
							</table>
						</form>
					</fieldset>
				</div>




			</div>
		</div>
	</sec:authorize>

</body>
</html>
