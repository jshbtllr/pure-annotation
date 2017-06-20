<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<title>Message</title>
	</head>
	<body>
		<h3>${message}</h3><br/>
		<h4>Redirecting in 2 seconds</h4>
		<meta http-equiv="refresh" content="2;url=${redirectView}"/>
	</body>
</html>
