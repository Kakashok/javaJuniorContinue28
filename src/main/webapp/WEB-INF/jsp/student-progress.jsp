<%@ page import="db.DBManager" %><%--
  Created by IntelliJ IDEA.
  User: Victor
  Date: 15.06.2022
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
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
    <title>Student Progress</title>
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
        <section class="content">
            <div class="side_menu">
                <div><a href="/">На главную</a></div>
                <div><a href="/students">Назад</a></div>
            </div>
            <div class="main">
                <h2>Отображена успеваемость для следующего студента:</h2>
                <div class="progress">
                    <input type="hidden" name="idStudent" value="1">
                    <table class="list">
                        <tr>
                            <th class="lp_col0">Фамилия</th>
                            <th class="lp_col1">Имя</th>
                            <th class="lp_col2">Группа</th>
                            <th class="lp_col3">Дата поступления</th>
                        </tr>
                        <tr>
                            <td class="lp_col0">${student.surname}</td>
                            <td class="lp_col1">${student.name}</td>
                            <td class="lp_col2">${student.group.group}</td>
                            <td class="lp_col3"><fmt:formatDate value="${student.date}" pattern="dd/MM/yyyy"/></td>
                        </tr>
                    </table>
                </div>
                <div class="progress_section">
                    <div class="marks">
                        <table class="list">
                            <tr>
                                <th class="lm_col0">Дисциплина</th>

                                <th class="lm_col1">Оценка</th>

                            </tr>

                            <c:forEach items="${marks}" var="m">

                                <tr>
                                    <td class="lm_col0">${m.discipline.discipline}</td>
                                    <c:if test="${m.mark ne -1}">
                                        <td class="lm_col1">${m.mark}</td>
                                    </c:if>
                                </tr>
                            </c:forEach>


                        </table>
                    </div>
                    <div class="term_average_mark">
                        <div class="term">
                            Выбрать семестр
                            <form action="/student-progress" method="get">
                                <label>
                                    <input type="hidden" name="idHiddenProgress" value="${student.id}">
                                    <select name="idTerm">

                                        <c:forEach items="${terms}" var="t">

                                            <c:choose>
                                                <c:when test="${t.id == selectedTerm.id}">
                                                    <option selected value="${t.id}">${t.term}</option>
                                                    <br/>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${t.id}">${t.term}</option>
                                                    <br/>
                                                </c:otherwise>
                                            </c:choose>


                                        </c:forEach>


                                    </select>
                                </label>
                                <input class="button_term" type="submit" value="Выбрать">
                            </form>
                        </div>
                        <div class="average_mark">
                            Средняя оценка за семестр:

                            <h3><fmt:formatNumber value="${avgMark}" maxFractionDigits="2"> </fmt:formatNumber> балла</h3>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
    <footer>
        <div>&copy; 2022 Victor Pomadchin</div>
    </footer>
</div>
</body>
</html>
