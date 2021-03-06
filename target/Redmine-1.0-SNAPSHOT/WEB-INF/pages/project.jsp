<%@ page import="az.mecid.models.Project" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            var tasksBlock=document.getElementById("tasksBlock");
            var usersFrame=document.getElementById("usersFrame");
            usersFrame.style.height=tasksBlock.clientHeight+10+'px';
        }
    </script>
</head>

<body class="" onload="resizing()">
<iframe src="/topFromProj" width="100%" height="170px" scrolling="no" border="0px"></iframe>

<div class="container">

    <div class="row">
        <div class="col-md-12" draggable="true" style="">
            <div class="" draggable="true">
                <div class="col-md-8 pull-left">
                    <br>
                    <c:forEach items="${tasksList}" var="task">
                        <c:choose>
                        <c:when test="${task.status!='Done'}">
                        <div class="well">
                        </c:when>
                        <c:otherwise>
                        <div class="well" style="background-color: darkgrey">
                        </c:otherwise>
                        </c:choose>
                            <font size="5"><a href="task/${task.id}">${task.title}</a></font> <div style="position:absolute;right:5%;">${task.status}</div>
                            <br>

                            <br>
                            <br>

                            <div class="col-md-6">
                                <p align="left"><font size="2">Здати до:</font></p>
                            </div>
                            <div class="col-md-6">
                                <p align="right"><font size="2">${task.dateOfCreating}</font></p>
                            </div>




                        </div>
                    </c:forEach>
                </div>
                <iframe src="/getUsers/${projectName}" style="border:0px; margin: 20px 0px 0px 0px"/> <!-- USERS    -->
            </div>
        </div>
    </div>
</div>

</body>

</html>
