<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix = "spring" uri = "http://www.springframework.org/tags"%>
<!DOCTYPE html>

<html>
	<head>
		<title><spring:message code="employee.management"/></title>
	</head>
	<body>
		<h3><spring:message code="employee.update"/></h3>
		<table width=100%>
			<td width=50% align="left">
				<a href="/employee"> <spring:message code="back"/> </a><br/>
			</td>
		</table>
		<div style="clear:both;"></div><br/>		
		<form action="/employee/update" method="POST">
			<table align="left" cellpadding="8" width=100%>
				<tr>
					<td> Name </td>
				</tr>
				<tr>
					<td align="center" width=20%> 
						<input type="text" name="title" value="${employee.name.title}" maxlength="255"/>
					</td>
					<td align="center" width=20%>
						<input type="text" name="firstName" value="${employee.name.firstName}" maxlength="255" required/>
					</td>
					<td align="center" width=20%>
						<input type="text" name="middleName" value="${employee.name.middleName}" maxlength="255" required/>
					</td>
					<td align="center" width=20%>
						<input type="text" name="lastName" value="${employee.name.lastName}" maxlength="255" required/>
					</td>
					<td align="center" width=20%>
						<input type="text" name="suffix" value="${employee.name.suffix}" maxlength="255"/>
					</td>
				</tr>
				<tr>
					<td align="center" width=20%> <spring:message code="title"/> </td>
					<td align="center" width=20%> <spring:message code="firstname"/> </td>
					<td align="center" width=20%> <spring:message code="middlename"/> </td>
					<td align="center" width=20%> <spring:message code="lastname"/> </td>
					<td align="center" width=20%> <spring:message code="suffix"/> </td>
				</tr>								
				<tr>
					<td> <spring:message code="address"/> </td>
				</tr>
				<tr>
					<td align="center" width=20%> 
						<input type="text" name="streetNumber" value="${employee.address.streetNumber}" maxlength="255" required/>
					</td>
					<td align="center" width=20%>
						<input type="text" name="barangay" value="${employee.address.barangay}" maxlength="255" required/>
					</td>
					<td align="center" width=20%>
						<input type="text" name="city" value="${employee.address.city}" maxlength="255" required/>
					</td>
					<td align="center" width=20%>
						<input type="text" name="country" value="${employee.address.country}" maxlength="255" required/>
					</td>
					<td align="center" width=20%>
						<input type="text" name="zipcode" value="${employee.address.zipcode}" maxlength="255" required/>
					</td>
				</tr>
				<tr>
					<td align="center" width=20%> <spring:message code="streetnumber"/> </td>
					<td align="center" width=20%> Barangay </td>
					<td align="center" width=20%> <spring:message code="city"/> </td>
					<td align="center" width=20%> <spring:message code="country"/> </td>
					<td align="center" width=20%> <spring:message code="zipcode"/> </td>
				</tr>				
				<tr>
					<td align="left" width=20%> <spring:message code="birthday"/> </td>
					<td align="left" width=20%>
						<input type="text" name="birthday" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${employee.birthday}"/>" maxlength="10" required/>
					</td>
					<td width=20%/>
					<td align="left" width=20%> <spring:message code="grade"/> </td>
					<td align="left" width=20%>
						<input type="text" name="gwa" value="${employee.gradeWeightAverage}" maxlength="6" required/>
					</td>
				</tr>
				<tr>
					<td align="left" width=20%> <spring:message code="employment.status"/> </td>
					<td align="center" width=20%>
						<input type="radio" name="employed" value="true" onclick="document.getElementById('hiredate').disabled = false;" required
						<core:if test="${employee.employed}">
								checked
						</core:if>
						> <spring:message code="yes"/> </input>
						<input type="radio" name="employed" value="false" onclick="document.getElementById('hiredate').disabled = true;" required
						<core:if test="${not employee.employed}">
								checked
						</core:if>
						> <spring:message code="no"/> </input>
					</td>
					<td width=20%/>
					<td width=20% align="left"> <spring:message code="hiredate"/> </td>
					<td width=20% align="left">
						<input type="text" name="hireDate" id="hiredate" maxlength="10" value=
						<core:choose>
							<core:when test="${employee.employed}">
								"<fmt:formatDate pattern="dd/MM/yyyy" value="${employee.hireDate}"/>"
							</core:when>
							<core:otherwise>
								""
							</core:otherwise>
						</core:choose>
						/>
					</td>
				</tr>
				<tr>
					<td align="left"> <spring:message code="update.current.contact"/> </td><br/>
				<!--	<core:forEach var="contact" items="${contacts}">-->
						<td>
							<select name="infoType">
								<option value="email"
									<core:if test="${contact.infoType == 'email'}">
										selected
									</core:if>
									> email 
								</option>
								<option value="telephone"
									<core:if test="${contact.infoType == 'telephone'}">
										selected
									</core:if>
									> <spring:message code="telephone"/> 
								</option>
								<option value="cellphone"
									<core:if test="${contact.infoType == 'cellphone'}">
										selected
									</core:if>
									> <spring:message code="cellphone"/> 
								</option>
							</select>
						</td>
						<td>
							<input type="text" name="infoDetail" value="${contact.infoDetail}" maxlength=255 required/>
						</td>
				<!--	</core:forEach>-->
				</tr>
				<tr width=50% align="left">
					<td align="left"> <spring:message code="update.current.role"/> </td><br/>
					<table width=50% border="1" align="center">
						<thead>
							<tr>
								<th> <spring:message code="role.code"/> </th>
								<th> <spring:message code="role.name"/> </th>
							</tr>
						</thead>
						<tbody>
							<core:forEach var="role" items="${roleList}">
								<tr>
									<td align="left">
										<input type="checkbox" name="roles" value="${role.id}"
											<core:forEach var="emprole" items="${currentRoles}">
												<core:if test="${role.id == emprole.id}">
													checked
												</core:if>
											</core:forEach>
										> 
										${role.roleCode}
									</td>
									<td>${role.roleName}</td>
								</tr>
							</core:forEach>
						</tbody>
					</table>
				</tr>
				<tr width=100% align="center">
					<input type="hidden" name="employeeId" value="${employee.id}"/>
					<input type="submit" value="<spring:message code="update"/>"/>
				</tr>
			</table>
		</form>
	</body>
</html>