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

<body style="border: 0px;" onload="resizing()">
<iframe src="top" width="100%" height="170px" scrolling="no" border="0px"></iframe>
<div class="container">

    <div class="row">
        <div class="col-md-12" draggable="true" style="">
            <div class="" draggable="true">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <h2>A lot of text</h2>
                    </div>
                </div>
                <div class="col-md-8 pull-left" id="tasksBlock">
                   <br>
                    <c:forEach items="${projectsList}" var="project">
                    <div class="well">
                        <font size="5"><a  href="projects/${project.id}">${project.name}</a></font>
                        <br>
                        <u>${project.description}</u>
                        <br>
                        <br>

                            <div class="col-md-6">
                                <p align="left"><font size="2">Здати до:</font></p>
                            </div>
                            <div class="col-md-6">
                                <p align="right"><font size="2">${project.manager.login}</font></p>
                            </div>




                    </div>
                    </c:forEach>

                </div>


                  <iframe src="/getUsers/0" style="border:0px; margin: 20px 0px 0px 0px" scrolling="no" id="usersFrame" onload="calculateUsersBlocksSize()"/> <!-- USERS   -->
                   </iframe>
                </div>
            </div>
        </div>
    </div>
</div>
</body>


</html>