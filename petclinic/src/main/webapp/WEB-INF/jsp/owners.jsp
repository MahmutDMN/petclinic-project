<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="tr">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Owners List</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f4f9;
                    margin: 0;
                    padding: 20px;
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                }

                table {
                    width: 80%;
                    border-collapse: collapse;
                    margin: 20px auto;
                    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                    background-color: white;
                }

                thead tr {
                    background-color: #007bff;
                    color: white;
                }

                thead tr td {
                    font-weight: bold;
                    padding: 10px;
                    text-align: left;
                }

                tbody tr {
                    text-align: left;
                }

                tbody tr:nth-child(even) {
                    background-color: #f2f2f2;
                }

                tbody tr:nth-child(odd) {
                    background-color: #ffffff;
                }

                td {
                    padding: 10px;
                    border: 1px solid #dddddd;
                }

                tbody tr:hover {
                    background-color: #e9ecef;
                }

                .button-container {
                    margin: 20px auto;
                    text-align: center;
                }

                .button {
                    text-decoration: none;
                    padding: 10px 20px;
                    background-color: #007bff;
                    color: white;
                    border-radius: 5px;
                    font-size: 16px;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
                }

                .button:hover {
                    background-color: #0056b3;
                }

                .update-button {
                    text-decoration: none;
                    padding: 5px 10px;
                    background-color: #28a745;
                    color: white;
                    border-radius: 3px;
                    font-size: 14px;
                    position: relative;
                }

                .update-button:hover {
                    background-color: #218838;
                }

                .update-button:hover::after {
                    content: "Kolaylik olsun diye bu buton yazilmistir.Url olarak da bu şekilde gidilebilir:http://localhost:8080/owners/update/{id}";
                    position: absolute;
                    top: -30px;
                    left: 50%;
                    transform: translateX(-50%);
                    background-color: #333;
                    color: white;
                    padding: 5px 10px;
                    border-radius: 5px;
                    font-size: 12px;
                    white-space: nowrap;
                    z-index: 10;
                }

                .delete-button {
                    text-decoration: none;
                    padding: 5px 10px;
                    background-color: #cc162c;
                    color: white;
                    border-radius: 3px;
                    font-size: 14px;
                    position: relative;
                }

                .delete-button:hover {
                    background-color: #cc162c;
                }

                .delete-button:hover::after {
                    content: "Kolaylik olsun diye bu buton yazilmistir.Url olarak da bu şekilde gidilebilir:http://localhost:8080/owners/delete/{id}";
                    position: absolute;
                    top: -30px;
                    left: 50%;
                    transform: translateX(-50%);
                    background-color: #333;
                    color: white;
                    padding: 5px 10px;
                    border-radius: 5px;
                    font-size: 12px;
                    white-space: nowrap;
                    z-index: 10;
                }
            </style>
        </head>

        <body>
            <div class="button-container">
                <a href="http://localhost:8080/owners/new" class="button">Add New Owner</a>
            </div>
            <table>
                <thead>
                    <tr>
                        <td>ID</td>
                        <td>First Name</td>
                        <td>Last Name</td>
                        <td>Actions</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${owners}" var="owner" varStatus="status">
                        <tr>
                            <td>${owner.id}</td>
                            <td>${owner.firstName}</td>
                            <td>${owner.lastName}</td>
                            <td>
                                <a href="http://localhost:8080/owners/update/${owner.id}"
                                    class="update-button">Update</a>
                                <a href="http://localhost:8080/owners/delete/${owner.id}"
                                    class="delete-button">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </body>

        </html>