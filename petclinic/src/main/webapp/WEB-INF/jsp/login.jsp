<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PetClinic Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-container {
            background: #fff;
            padding: 20px 30px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        .login-container h1 {
            font-size: 24px;
            color: #333;
            margin-bottom: 20px;
        }

        .login-container form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .login-container input[type="text"],
        .login-container input[type="password"] {
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
            width: 100%;
            box-sizing: border-box;
        }

        .login-container input[type="checkbox"] {
            margin-right: 10px;
        }

        .login-container label {
            font-size: 14px;
            color: #555;
            display: flex;
            align-items: center;
            justify-content: start;
        }

        .login-container input[type="submit"] {
            padding: 10px;
            font-size: 16px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .login-container input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .error-message {
            color: red;
            font-size: 14px;
        }
    </style>
</head>

<body>
    <div class="login-container">
        <h1>PetClinic Login Page</h1>
        <form action="login" method="post">
            <input name="username" type="text" placeholder="Username" required />
            <input name="password" type="password" placeholder="Password" required />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <label>
                <input name="remember-me" type="checkbox" />
                Remember Me
            </label>
            <input type="submit" value="Login">
        </form>
        <c:if test="${not empty param.loginFailed}">
            <p class="error-message">
                <c:out value="Login Failed, Incorrect Username or Password"></c:out>
            </p>
        </c:if>
    </div>
</body>

</html>
