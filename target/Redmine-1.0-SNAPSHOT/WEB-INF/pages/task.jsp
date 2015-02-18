<%@ page import="az.mecid.models.Project" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style type="text/css">
      .firstSentence{
           margin-top: 100px;

      }
    </style>
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        function resizing() {
            var tasksBlock=document.getElementById("tasksBlock");
            var usersFrame=document.getElementById("usersFrame");
            usersFrame.style.height=tasksBlock.clientHeight+10+'px';
        }
    </script>
</head>

<body class="" onload="resizing()">
<iframe src="/topFromProj?project=${task.project.id}" width="100%" height="90px" scrolling="no" border="0px"></iframe>
<div class="container">
    <div class="row">
        <div class="col-md-12" >
            <ul class="lead nav nav-pills">
                <li class="active">
                    <a href="/home" target="_top">Home</a>
                </li>
                <li class="">
                    <a href="/projects" target="_top">Projects</a>
                </li>
                <li class="">
                    <a href="/userinfo/1" target="_top">Users</a>
                </li>
                <li class="">
                    <a href="/form/editProject/0" target="_top">New project</a>
                </li>
                <li class="" id="taskButt">
                    <a href="/form/createTask/${task.project.id}" target="_top">New task</a>
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
                                <td width="30%" style="text-align: left;vertical-align: top"> <font size="2" color="#a9a9a9">Created ${task.dateOfCreating}</font></td>
                                <td width="30%" style="vertical-align: top;font-size: 40px"> <center>  ${task.title}</center></td>
                                <td width="30%" style="text-align: right;vertical-align: top"> <font  size="2"color="#a9a9a9">by ${task.creator.login} </font></td>
                            </tr>
                        </table>


                    Статус:          ${task.status}                       <br>
                       <p style="margin: 10px auto; padding: 10px 7px; box-shadow: 0 0 10px 5px rgba(221, 221, 221, 1);">${task.description}</p>

                    <div style="width: 100%; margin: 3em auto; padding: 1em; box-shadow:
   -20px 20px 0 -17px #f5f5f5, 20px -20px 0 -17px #f5f5f5, 20px 20px 0 -20px #c27153, 0 0 0 2px #c27153; word-wrap:break-word ">
                        <c:forEach items="${comments}" var="comment">

                                <c:choose>
                                       <c:when test="${oldDate!=comment.date}">
                                           <p>${comment.date} <b>${comment.user}</b>: ${comment.text}</p>
                                       </c:when>
                                       <c:otherwise>
                                           <p style="text-indent: 80px"> <b>${comment.user}</b>: ${comment.text}</p>
                                       </c:otherwise>
                                </c:choose>
                            <c:set var="oldDate" value="${comment.date}"></c:set>
                        </c:forEach>
                        <c:remove var="oldDate"></c:remove>

                       <!-- <p>2014-12-25 <b>Wayruha</b>: awapdjwaorhawiu rgwirgsueirgigtreugh yswahbedufrdiygrhuio</p>
                        <p style="text-indent: 80px"> JWUshdgnfjzhunjhghrunjs damujfehnr enjwmksmejfhrnk</p>  <p style="text-indent: 80px"> JWUshdgnfjzhunjhghrunjs damujfehnr enjwmksmejfhrnk</p>
                        <p style="text-indent: 80px"> <b>System</b>: Змінено основного виконавця</p>   -->
                    </div>
                         <br>
                        <form action="/projects/addComment?taskId=4&login=wayruha" method="get">
                            <table style="width: 100%;" >
                                <tr>
                                    <td padding="20" style="margin-right:30px" ><textarea class="form-control" name="text" id="textarea" type="text" placeholder="Leave your comment" rows="4" onfocus="this.value=''"></textarea>    </td>
                                    <td style="width: 10%; padding:30px"><input type="submit" class="btn-lg" style="" value="Post"></td>
                                    <input type="hidden" name="taskId" value="${task.id}">
                                    <input type="hidden" name="login" value="LOGIN">
                                </tr>
                            </table>
                        </form>
                        <p style="text-align: left;vertical-align: top"> <font size="2" color="#a9a9a9">Have been working for 122hours </font></p> <p style="text-align: right">Mark as done</p>
    </div>
</div>
<iframe src="/getUsers/${task.project.id}"  style="border:0px; margin: 20px 0px 0px 0px; height: 600px;"/> <!-- USERS    -->
</div>
        </div>
    </div>
</div>

</body>

</html>
