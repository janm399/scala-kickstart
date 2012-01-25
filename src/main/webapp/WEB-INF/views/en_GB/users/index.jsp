<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>All users</title>
</head>
<body>
<table>
	<tr>
		<td>Id</td>
		<td>First name</td>
		<td>Last name</td>
		<td>&nbsp;</td>
	</tr>
	<c:forEach items="${userList}" var="u">
		<tr>
			<td><c:out value="${u.id}"/></td>
			<td><c:out value="${u.firstName}"/></td>
			<td><c:out value="${u.lastName}"/></td>
			<td><a href="users/${u.id}.html">Edit</a></td>
		</tr>
	</c:forEach>
</table>
<a href="add.html">Add new user</a>

</body>
</html>