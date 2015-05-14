<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


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
        function generateCode() {
            var length = 12;
            var code = "";
            var characters = "0123456789abcdefghijklmnopqrstuvwxyz";
           /* var charArr = characters.toCharArray();*/
            //var rand = new Random();
            for (var i = 0; i < length; i++)
                code += characters[Math.floor(Math.random() * characters.length)];
            var codeInput=document.getElementById("generatedCode");

            codeInput.value=code;
        }
    </script>
</head>

<body class="">
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
                    <a href="/form/createTask/${project.id}" target="_top">New task</a>
                </li>
                <li class="pull-right">
                    <a href="/form/createProject" target="_top">New project</a>
                </li>
                <li class="pull-right">
                    <a href="/userinfo/1" target="_top">Users</a>
                </li>
            </ul>
        </div>
    </div>

    <div class="container">

        <div class="row">
            <div class="col-md-12" draggable="true" style="">
                <div class="" draggable="true">
                    <div class="col-md-8 pull-left">
                        <div class="well well-lg">
                            <h2> Invite new user in system</h2>  <br>

                            <c:choose>
                                <c:when test="${status=='error'}"><font color="red">Unknown error. Message wasn`t sent</font></c:when>
                                <c:when test="${status=='success'}"><font color="gren">Message was successfully sent</font></c:when>
                            </c:choose>

                            <form action="/inviteUser" method="post">

                                <div class="form-group">

                                    <br>
                                    <hr>
                                    <div class="col-sm-4">
                                        <label for="e_mail" class="control-label">E-mail</label>
                                    </div>
                                    <div class="col-sm-6">
                                        <input type="text" name="e_mail" id="e_mail" class="form-control"
                                               placeholder="Enter e-mail">
                                    </div>
                                    <br>
                                    <hr>
                                    <div class="col-sm-4">
                                        <label class="control-label">Type</label>
                                    </div>
                                    <div class="col-sm-6">
                                        <input  type="radio" name="isManager" value="${true}" >Manager
                                        &nbsp;&nbsp;<input  type="radio" name="isManager" value="${false}" title="Employee"> Employee

                                    </div>
                                </div>
                                <input type="submit">
                            </form>

                        </div>
                        <%--   <form:form action="/form/saveProject" modelAttribute="ProjectForm" class="form-horizontal" role="form" name="form">
                           <form:input path="e_mail" class="form-control" id="e_mail" value="${project.title}"/>--%>

                    </div>
                    <%--   <iframe src="/getUsers/all" style="border:0px; margin: 20px 0px 0px 0px" />--%>


                </div>
            </div>
        </div>
    </div>
</div>
</body>


</html>