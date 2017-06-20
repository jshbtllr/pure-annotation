<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib prefix = "spring" uri = "http://www.springframework.org/tags"%>
<!DOCTYPE html>

<html>
	<head>
		<title><spring:message code="role.title"/></title>
	</head>
	<body>
		<h3><spring:message code="role.update"/></h3>
		<table width=100%>
			<td width=50% align="left">
				<a href="/roles"> <spring:message code="back.roles"/> </a><br/>
			</td>
		</table>
		<div style="clear:both;"></div><br/>			
		<form action="/roles/update" method="POST">
			<spring:message code="role.code"/>: <input type="text" name="roleCode" value="${role.roleCode}" maxlength=255/><br/>
			<spring:message code="role.name"/>: <input type="text" name="roleName" value="${role.roleName}" maxlength=255/><br/>
			<input type="hidden" name="roleId" value="${role.id}"/>
			<input type="submit" value="<spring:message code="role.update"/>"/>
		</form>
	</body>
</html>