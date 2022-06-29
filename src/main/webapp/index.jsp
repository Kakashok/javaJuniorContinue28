<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Title page</title>
    <link rel="stylesheet" href="../../resourses/fonts/fonts.css">
    <link rel="stylesheet" type="text/css" href="../../resourses/css/style.css">
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
                </c:choose>

            </div>
        </nav>
    </header>
    <main>
        <section class="menu_title">
            <a href="/students">Студенты</a>
            <a href="/disciplines">Дисциплины</a>
            <a href="/terms">Семестры</a>
        </section>
    </main>
    <footer>
        <div>&copy; 2022 Victor Pomadchin</div>
    </footer>
</div>
</body>
</html>