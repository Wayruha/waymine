<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="az.mecid.enums.Access" %>
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
    <style type="text/css">
        .firstSentence {
            margin-top: 100px;

        }
    </style>
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
<iframe src="/topFromProj?project=${task.project.id}" width="100%" height="90px" scrolling="no" border="0px"></iframe>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <ul class="lead nav nav-pills">
                <li class="active">
                    <a href="/home" target="_top">Home</a>
                </li>
                <li class="">
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
                <div class="col-md-8 pull-left">
                    <br>

                    <div class="well" style="border-radius:10px">
                        <table width="100%">
                            <tr>
                                <td width="30%" style="text-align: left;vertical-align: top"><font size="2"
                                                                                                   color="#a9a9a9">Created ${task.dateOfCreating}</font>
                                </td>
                                <td width="30%" style="vertical-align: top;font-size: 40px">
                                    <center> ${task.title}</center>
                                </td>
                                <td width="30%" style="text-align: right;vertical-align: top"><font size="2"
                                                                                                    color="#a9a9a9">by ${task.creator.login} </font>
                                </td>
                            </tr>
                        </table>


                        Статус: ${task.status} <br>

                        <p style="margin: 10px auto; padding: 10px 7px; box-shadow: 0 0 10px 5px rgba(221, 221, 221, 1);">${task.description} <br/><u>Час виконання:</u> ${task.plannedTime} годин</p>

                <c:if test="${fn:length(comments)>0}">
                        <div style="width: 100%; margin: 3em auto; padding: 1em; box-shadow:
                            -20px 20px 0 -17px #f5f5f5, 20px -20px 0 -17px #f5f5f5, 20px 20px 0 -20px #c27153, 0 0 0 2px #c27153; word-wrap:break-word ">
                            <c:forEach items="${comments}" var="comment">
                                <c:choose>
                                    <c:when test="${oldDate!=comment.date}">
                                        <p>${comment.date} <b>${comment.user}</b>: ${comment.text}</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p style="text-indent: 80px"><b>${comment.user}</b>: ${comment.text}</p>
                                    </c:otherwise>
                                </c:choose>
                                <c:set var="oldDate" value="${comment.date}"></c:set>
                            </c:forEach>
                            <c:remove var="oldDate"></c:remove>


                            <!-- <p>2014-12-25 <b>Wayruha</b>: awapdjwaorhawiu rgwirgsueirgigtreugh yswahbedufrdiygrhuio</p>
                             <p style="text-indent: 80px"> JWUshdgnfjzhunjhghrunjs damujfehnr enjwmksmejfhrnk</p>  <p style="text-indent: 80px"> JWUshdgnfjzhunjhghrunjs damujfehnr enjwmksmejfhrnk</p>
                             <p style="text-indent: 80px"> <b>System</b>: Змінено основного виконавця</p>   -->
                        </div>
                </c:if>
                        <br>
                <c:if test="${role!=null}">
                        <form action="/projects/addComment?taskId=${task.id}&login=${t_u.user.login}" method="get">
                            <table style="width: 100%;">
                                <tr>
                                    <td padding="20" style="margin-right:30px"><textarea class="form-control"
                                                                                         name="text" id="textarea"
                                                                                         type="text"
                                                                                         placeholder="Leave your comment"
                                                                                         rows="4"
                                                                                         onfocus="this.value=''"></textarea>
                                    </td>
                                    <td style="width: 10%; padding:30px"><input type="submit" class="btn-lg" style=""
                                                                                value="Post"></td>
                                    <input type="hidden" name="taskId" value="${task.id}">

                                </tr>
                            </table>
                        </form>
                </c:if>
                        <c:if test="${t_u!=null}">  <p style="text-align: left;vertical-align: top"><font size="2" color="#a9a9a9">Have been working
                            for ${t_u.spentTime} hours. Last fixed time at ${t_u.lastFixedActivity} </font></p>

                       <form action="/saveWorkedTime/${t_u.task.id}/${t_u.spentTime}" method="post">
                            <input class="form-contol" type="number" min="0" max="24" name="workedTime">
                            <input type="submit" class="btn btn-success btn-sm" value="Save worked time">
                        </form>                   </c:if>

                        <c:if test="${role=='Owner'}"><p style="text-align: right"><a class="btn btn-success btn-sm">Mark as done</a></p></c:if>
                    </div>
                <c:if test="${fn:length(history)>0}">
                    <div style="width: 100%; margin: 3em auto; padding: 1em; box-shadow:
                         -20px 20px 0 -17px #f5f5f5, 20px -20px 0 -17px #f5f5f5, 20px 20px 0 -20px #c27153, 0 0 0 2px #c27153; word-wrap:break-word;">
                        <c:forEach items="${history}" var="historyPost">
                            <p><b>${historyPost.actor.login}</b> <spring:message code="history.${historyPost.action}"/>:
                                <u> ${historyPost.object}</u></p>
                        </c:forEach>
                    </div>
                </c:if>
                </div>
                <iframe src="/getUsersInTask/${task.id}" style="border:0px; margin: 20px 0px 0px 0px; height: 600px;"/>
                <!-- USERS    -->
            </div>
        </div>
    </div>
</div>

</body>

</html>
