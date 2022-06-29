<%--
  Created by IntelliJ IDEA.
  User: Victor
  Date: 28.05.2022
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Students List</title>
    <link rel="stylesheet" href="../../resourses/fonts/fonts.css">
    <link rel="stylesheet" type="text/css" href="../../resourses/css/style.css">
    <script src="../../resourses/js/functions.js"></script>

</head>
<body>
<div id="container">
    <header>
        <nav class="head">
            <h1 class="title">Система управления студентами и их успеваемостью</h1>
            <div class="login">


                <c:choose>
                    <c:when test="${isLogin eq true}">
                        <div><p>Привет, admin!</p></div>
                        <div><a href="/logout">Logout</a></div>
                    </c:when>

                    <c:otherwise>
                        <div><a href="/login">Login</a></div>
                    </c:otherwise>
                </c:choose>            </div>
        </nav>
    </header>
    <main>
        <section class="content">
            <div class="side_menu">
                <div><a href="/home">На главную</a></div>
            </div>
            <div class="main">
                <div class="button_group1">

                    <input class="button_student1" type="submit"
                           value="Просмотреть успеваемость выбранных студентов" onclick="progressStudent()">
                    <form action = "/student-create" method="get">
                        <input class="button_student2" type="submit" value="Создать студента…">

                    </form>



                    <input class="button_student1" type="submit" value="Модифицировать выбранного студента…"
                           onclick="modifyStudent()">
                    <input class="button_student2" type="submit" value="Удалить выбранных студентов"
                           onclick="deleteStudents()">

                </div>
                <div class="students">
                    <table class="list">
                        <caption>Список студентов</caption>
                        <tr>

                            <th class="l_col0"></th>

                            <th class="l_col1">Фамилия</th>
                            <th class="l_col2">Имя</th>
                            <th class="l_col3">Группа</th>
                            <th class="l_col4">Дата поступления</th>
                        </tr>
                        <c:forEach items = "${students}" var = "s">
                            <tr>

                                <td class="l_col0"><label><input name="idStudent" type="checkbox" value="${s.id}"></label></td>

                                <td class="l_col1">${s.surname}</td>
                                <td class="l_col2">${s.name}</td>
                                <td class="l_col3">${s.group.group}</td>
                                <td class="l_col4"><fmt:formatDate value="${s.date}" pattern = "dd/MM/yyyy"/></td>
                            </tr>
                        </c:forEach>



                    </table>
                </div>
            </div>
        </section>
    </main>
</div>
</body>

<form action = "/student-delete" method="post" id="deleteForm">
    <input type = "hidden" name="idsHiddenDelete" id = "idsHiddenDelete">
</form>
<form action = "/student-modify" method="get" id="modifyForm">
    <input type = "hidden" name="idsHiddenModify" id = "idsHiddenModify">
</form>
<form action = "/student-progress" method="get" id="progressForm">
    <input type = "hidden" name="idHiddenProgress" id = "idHiddenProgress">
</form>

</html>