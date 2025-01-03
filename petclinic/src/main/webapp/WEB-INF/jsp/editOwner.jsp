<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <!DOCTYPE html>
        <html lang="tr">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Create Owner</title>
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

                .form-container {
                    background: #ffffff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                    width: 100%;
                    max-width: 400px;
                }

                .form-container h1 {
                    font-size: 1.5rem;
                    margin-bottom: 20px;
                    text-align: center;
                    color: #333333;
                }

                .form-group {
                    margin-bottom: 15px;
                }

                .form-group label {
                    display: block;
                    font-weight: bold;
                    margin-bottom: 5px;
                    color: #555555;
                }

                .form-group input {
                    width: 100%;
                    padding: 10px;
                    font-size: 1rem;
                    border: 1px solid #cccccc;
                    border-radius: 4px;
                    box-sizing: border-box;
                }

                .form-group input:focus {
                    outline: none;
                    border-color: #007bff;
                    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
                }

                .form-button {
                    display: flex;
                    justify-content: center;
                    margin-top: 20px;
                }

                .form-button button {
                    background-color: #007bff;
                    color: white;
                    padding: 10px 20px;
                    font-size: 1rem;
                    border: none;
                    border-radius: 4px;
                    cursor: pointer;
                    transition: background-color 0.3s;
                }

                .form-button button:hover {
                    background-color: #0056b3;
                }
            </style>
        </head>

        <body>
            <div class="form-container">
                <h1>Update Owner</h1>
                <form:form modelAttribute="owner" method="post">
                    <div class="form-group">
                        <label for="firstName">First Name:</label>
                        <form:input path="firstName" id="firstName" />
                    </div>
                    <div class="form-group">
                        <label for="lastName">Last Name:</label>
                        <form:input path="lastName" id="lastName" />
                    </div>
                    <div class="form-button">
                        <form:button name="submit">Update</form:button>
                    </div>
                </form:form>
            </div>
        </body>

        </html>