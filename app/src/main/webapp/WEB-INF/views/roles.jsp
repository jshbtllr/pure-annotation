<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix = "spring" uri = "http://www.springframework.org/tags"%>
<%@taglib prefix = "security" uri = "http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>
	<head>
		<title><spring:message code="role.title"/></title>
	</head>
	<body>
		<h3><spring:message code="role.title"/></h3>
		<h4><spring:message code="role.head"/></h4>
		<table width=100%>
			<td width=20% align="left">
				<a href="/employee"> <spring:message code="back"/> </a>
			</td>
			<security:authorize access="hasRole('ADMIN')">
				<td width=20% align="center">
					<a href="/roles/add"><spring:message code="role.link"/></a>
				</td>
			</security:authorize>
			<td width=20% align="center">
				Language: <a href="?language=en"> <spring:message code="english"/> </a> | <a href="?language=de"> <spring:message code="german"/> </a>
			</td>
			<td width=40% align="right">
				<form action="roles" method="GET">
					<spring:message code="sortby"/><select name="sort">
						<option value="id"><spring:message code="role.id"/></option>
						<option value="code"><spring:message code="role.code"/></option>
						<option value="name"><spring:message code="role.name"/></option>
					</select>
					<select name="order">
						<option value="ascending"><spring:message code="ascending"/></option>
						<option value="descending"><spring:message code="descending"/></option>
					</select>
					<input type="submit" value="<spring:message code="sort"/>"/>
				</form>
			</td>
		</table>

		<div style="clear:both"></div><br/>
		<table width="60%" border="1" align="left">
			<thead>
				<tr>
					<th width=8%><spring:message code="role.id"/></th>
					<th width=15%><spring:message code="role.code"/></th>
					<th width=25%><spring:message code="role.name"/></th>
					<security:authorize access="hasRole('ADMIN')">
						<th width=15%><spring:message code="action"/></th>
					</security:authorize>
				</tr>
			</thead>
			<tbody>
				<core:forEach var="role" items="${roles}">
					<tr>
						<td align="center">${role.id}</td>
						<td align="center">${role.roleCode}</td>
						<td align="center">${role.roleName}</td>
						<security:authorize access="hasRole('ADMIN')">
							<td align="center">
								<form roles="/roles" method="POST">
									<input type="hidden" name="roleId" value="${role.id}"/>
									<input type="submit" value="<spring:message code="delete"/>"/>
								</form>
								<form action="/roles/update" method="GET">
									<input type="hidden" name="roleId" value="${role.id}"/>
									<input type="submit" value="<spring:message code="update"/>"/>
								</form>
							</td>
						</security:authorize>
					</tr>
				</core:forEach>
			</tbody>
		</table>
	</body>
</html>