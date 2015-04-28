<%@ page import="az.mecid.models.User" %>
<%@ page import="az.mecid.enums.Access" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Userinfo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
          rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</head>

<body >
<iframe src="/top" name="usersFrame" width="100%" height="90px" scrolling="no"
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
                <li class="pull-right active">
                    <a href="#" target="_top">Users</a>
                </li>
            </ul>
        </div>
    </div>


    <div class="row">
        <div class="col-md-12" draggable="true" style="">
            <div class="" draggable="true">
                <div class="row">

                </div>
                <div class="col-md-8 pull-left well" id="tasksBlock">
                     <table width="100%">
                         <tr>
                             <td ><h4><b>${user.secondName} ${user.firstName}</b></h4></td>
                             <td align="right"><h4><b>${user.login}</b></h4></td>
                         </tr>
                     </table>

                    Ел.адреса:${user.eMail}         <br/>
                    Таски в яких задіяний:<br/>
                    <ul>
                        <br>
                        <table width="100%">
                            <tr><td width=20%>Проект</td> <td width="40%">Завдання</td> <td width="10%">Роль</td><td width="10%">Відпрацьовано</td><td width="10%">Статус таску</td></tr>
                            <tr><td colspan="5"><hr></td></tr>
                    <c:forEach items="${task_userList}" var="t_u">
                            <tr>
                            <td width="20%"><b>${t_u.task.project.name}</b></td> <td width="40%">${t_u.task.title}</td> <td width="5%">${t_u.access}</td> <td width="5%" align="center"><c:if test="${t_u.access=='Owner'}">${t_u.spentTime} год.</c:if></td><td width="15%"><u>${t_u.task.status}</u></td>

                            </tr>
                            <tr><td width="100%" colspan="5"><hr></td></tr>

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


                <iframe src="/getUsers/0" style="border:0px; margin: 20px 0px 0px 0px" scrolling="auto" height="400"/> <!-- USERS   -->
                </iframe>
            </div>
        </div>
    </div>
</div>
</div>
</body>


</html>