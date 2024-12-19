<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Spring Boot HTML</title>
</head>

<body>
	<h1>index page</h1>
	<form action="logout" method="post">
		<input type="submit" value="logout"/>
		<input type="hidden" name=${_csrf.parameterName} value="${_csrf.token}"/>
</body>

</html>