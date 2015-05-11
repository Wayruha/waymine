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
    <title>User</title>
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
                <li class="pull-right" id="taskButt">
                    <a href="/form/createTask/${projectId}" target="_top">New task</a>
                </li>
                <li class="pull-right">
                    <a href="/form/createProject" target="_top">New project</a>
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

                    E-mail : <u>${user.eMail}</u>
                    <h3><small>Involved in</small></h3>
                    <ul>
                        <br>
                        <table width="100%" class="table table-bordered">
                            <tr><th width=20%>Project</th> <th width="40%">Task</th> <th width="10%">Role</th><th width="10%">Spent time</th><th width="10%">Task status</th></tr>

                    <c:forEach items="${task_userList}" var="t_u">
                            <tr>
                            <th width="20%"><b>${t_u.task.project.name}</b></th> <th width="40%">${t_u.task.title}</th> <th width="5%">${t_u.access}</th> <th width="5%" align="center"><c:if test="${t_u.access=='Owner'}">${t_u.spentTime} год.</c:if></th><th width="15%"><u>${t_u.task.status}</u></th>

                            </tr>


                    </c:forEach>

                        </table>
                    </ul>
                    <br><br>

                    <c:if test="${fn:length(user.projectList) gt 0}">
                    <ul>
                         Manage projects :

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