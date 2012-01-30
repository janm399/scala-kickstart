<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<link rel="stylesheet" href="/css/jquery-ui-1.8.5.custom.css" type="text/css">
	<link rel="stylesheet" href="/css/main.css" type="text/css">
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
			<td><form action="${u.id}.html" method="post" name="actions">
					<input type="hidden" name="_method" value="delete"/>
					<input type="submit" value="Delete"/>&nbsp;
					<a href="${u.id}.html">Edit</a>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>
<a href="add.html">Add new user</a>

</body>
</html>