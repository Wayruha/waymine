<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>All projects</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
          rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        function resizing() {
            var tasksBlock = document.getElementById("tasksBlock");
            var usersFrame = document.getElementById("usersFrame");
            usersFrame.style.height = tasksBlock.clientHeight + 10 + 'px';

        }
    </script>
</head>

<body class="" onload="resizing()">
<iframe src="/topFromProj?project=${project.id}" name="usersFrame" width="100%" height="90px" scrolling="no"
        border="0px"></iframe>

<div class="container">

    <div class="row">
        <div class="col-md-12 ">
            <ul class="lead nav nav-pills">
                <li class="">
                    <a href="/home" target="_top">Home</a>
                </li>
                <li>
                    <a href="/projects" target="_top">Projects</a>
                </li>

                <sec:authorize url="/form">
                    <li class="pull-right" id="taskButt">
                        <a href="/form/createTask/${project.id}" target="_top">New task</a>
                    </li>

                    <li class="pull-right">
                        <a href="/form/createProject" target="_top">New project</a>
                    </li>
                </sec:authorize>
                <li class="pull-right">
                    <a href="/userinfo/1" target="_top">Users</a>
                </li>
            </ul>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12" draggable="true" style="">
            <div class="" draggable="true">
                <div class="col-md-8 pull-left well" id="tasksBlock">

                   Менеджер <b>${project.manager.login}</b>        <br/>
                    Дата створення ${project.createdDate}<br/>
                    <ul>
                        <br>
                        <table width="100%">
                            <tr><td width="30%">Таск</td> <td width="5%">Статус</td><td width="20%"  align="center">Витрачено</td><td width="10%">Дата створення</td></tr>
                            <tr><td colspan="4"><hr></td></tr>
                            <%--<div class="col-md-12">
                                <div class="progress">
                                    <div class="progress-bar progress-bar-info" role="progressbar" style="width: 20%;">60% Complete</div>
                                </div>
                            </div>--%>

                            <c:forEach var="i" begin="0" end="${fn:length(tasksList)}">
                               <c:set var="progress" value="${tasksList[i].plannedTime==0?0:spentTimeList[i]/tasksList[i].plannedTime*100}" > </c:set>
                               <%-- <c:set var="val" value="${tasksList[i].plannedTime==0?0:spentTimeList[i]/tasksList[i].plannedTime*100}" > </c:set>
                                <c:set var="progress" value="${fmt:formatNumber value=${val} maxFractionDigits="2""}" >  </c:set>--%>

                                <tr>
                                    <td width="30%"><b>${tasksList[i].title}</b></td> <td width="5%">${tasksList[i].status}</td> <td width="20%"  align="center">${spentTimeList[i]}/${tasksList[i].plannedTime} год. <div class="progress"> <div  <c:if test="${progress==100}">class="progress-bar progress-bar-success"</c:if> class="progress-bar progress-bar-info" role="progressbar"  style="width:${progress}%"><c:if test="${progress!=0}">${progress}%</c:if></div></div></td> <td width="10%" align="center">${tasksList[i].dateOfCreating}</td>
                                </tr>
                                <tr><td width="100%" colspan="4"><hr></td></tr>


                            </c:forEach>

                        </table>
                    </ul>
                    <br><br>

                    <c:if test="${fn:length(user.projectList) gt 0}">
                        <ul>
                            Є менеджером проектів :

                            <c:forEach items="${user.projectList}" var="project">
                                <li>
                                    <b>${project.name}</b>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </div>
                    <iframe src="/getUsers/${project.id}" id="usersFrame" style="border:0px; margin: 20px 0px 0px 0px"/>
                    <!-- USERS    -->
                </div>
            </div>
        </div>
    </div>

</body>

</html>
