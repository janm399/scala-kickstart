<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<link rel="stylesheet" href="/css/jquery-ui-1.8.5.custom.css" type="text/css"/>
	<link rel="stylesheet" href="/css/main.css" type="text/css"/>
	<title>Users</title>
</head>
<body>
<div class="body">
    <sf:form action="/users/" method="post" commandName="user">
		<sf:hidden path="id"/>
		<sf:hidden path="version"/>

		<div class="ui-widget">
			<sf:label path="username">Username</sf:label> <sf:input path="username"/><sf:errors path="username"/>
		</div>
		<div class="ui-widget">
			<sf:label path="firstName">First Name</sf:label> <sf:input path="firstName"/><sf:errors path="firstName"/>
		</div>
		<div class="ui-widget">
			<sf:label path="lastName">Last Name</sf:label> <sf:input path="lastName"/><sf:errors path="lastName"/>
		</div>

		<input type="submit"/>
	</sf:form>
</div>
</body>
</html>