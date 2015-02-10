<%@ page import="az.mecid.models.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Bootstrap, from Twitter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
          rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>

<body>
<iframe src="/redirect/top" width="100%" height="90px" scrolling="no" border="0px"></iframe>
<div class="container">
    <div class="row">
        <div class="col-md-12" style="min-height:60px">
            <ul class="lead nav nav-pills">
                <li class="active">
                    <a href="#">Home</a>
                </li>
                <li class="">
                    <a href="#">Profile</a>
                </li>
                <li class="">
                    <a href="#">Messages</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12" draggable="true" style="">
            <div class="" draggable="true">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <h2>A lot of text</h2>
                    </div>
                </div>
                <div class="col-md-8 pull-left" id="tasksBlock">

                    <center><h1><b>${user.login}</b></h1></center>
                          Іфна про юзера.   <br/>
                    Імя:${user.firstName}   <br/>
                    Прізвище:${user.secondName} <br/>
                    Ел.адреса:${user.eMail}         <br/>
                    Таски в яких задіяний:<br/>
                    <ul>
                    <c:forEach items="${task_userList}" var="t_u">

                            <li>Проект:<b>${t_u.task.project.name}</b>->${t_u.task.title}; Роль : ${t_u.access}; Статус таску : <u>${t_u.task.status}</u></li>

                    </c:forEach>
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


                <iframe src="/getUsers/all" style="border:0px; margin: 20px 0px 0px 0px" scrolling="auto" height="400"/> <!-- USERS   -->
                </iframe>
            </div>
        </div>
    </div>
</div>
</div>
</body>


</html>