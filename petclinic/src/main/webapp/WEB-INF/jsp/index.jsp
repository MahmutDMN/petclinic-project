<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Spring Boot HTML</title>
	<style>
		/* Genel stil */
		body {
			font-family: Arial, sans-serif;
			background-color: #f9f9f9;
			color: #333;
			margin: 20px;
			line-height: 1.6;
		}

		h1 {
			text-align: center;
			color: #007BFF;
			margin-bottom: 20px;
		}

		h5 {
			background-color: #ffffff;
			padding: 10px 15px;
			border-radius: 5px;
			border: 1px solid #ddd;
			margin-bottom: 10px;
			box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
		}

		form {
			display: flex;
			flex-direction: column;
			align-items: flex-start;
			padding: 10px;
			border: 1px solid #ccc;
			background-color: #ffffff;
			border-radius: 5px;
			max-width: 300px;
			margin: 20px auto;
			box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
		}

		input[type="submit"] {
			background-color: #007BFF;
			color: #ffffff;
			border: none;
			padding: 10px 15px;
			border-radius: 5px;
			cursor: pointer;
			font-size: 14px;
			transition: background-color 0.3s;
		}

		input[type="submit"]:hover {
			background-color: #0056b3;
		}

		input[type="hidden"] {
			display: none;
		}

		span {
			font-size: 12px;
			color: #555;
			margin-top: 10px;
		}
	</style>
</head>

<body>
	<h1>Merhaba Dunya</h1>
	<h5>1-JAVA Server Pages (JSP) Web Container'larin executable JAR icerisindeki JSP sayfalarina erisiminde sorunlar
		olabilmektedir.
		Thymeleaf, Freemarker gibi templatelar kullanilabilir. JSP sayfalari src/main/webapp dizini altinda bir
		lokasyonda olusturulmalidir. Dolayisiyla
		pom.xml icerisinde projenin packaging tipi war olmalidir.
		<!--<packaging>war</packaging>-->
	</h5>
	<h5>
		2-Tomcat ile calisirken JSP sayfalarinin derlenebilmesi icin Jasper compiler'in projenin classpath (pom.xml)
		icerisinde yer
		almasi gereklidir.
		<!--
<dependency>
<groupId>org.apache.tomcat.embed</groupId>
<artifactId>tomcat-embed-jasper</artifactId>
</dependency>
        -->
	</h5>
	<h5>
		Logout kismi
		<br>
		<form action="logout" method="post">
			<input type="submit" value="Logout" />
			<br>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<span>CSRF Token: ${_csrf.token}</span>
		</form>
	</h5>
</body>

</html>