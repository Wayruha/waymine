<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Registration WayMine</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
          rel="stylesheet" type="text/css">
    <style type="text/css" rel="stylesheet">
        .error{
            color:red;
        }
    </style>
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        function validate(){
            var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
            var firstName=document.getElementById("firstName").value;
            var secondName=document.getElementById("secondName").value;
            var pass=document.getElementById("password").value;
            var pass2=document.getElementById("password2").value;
            var eMail=document.getElementById("eMail").value;
            var nameLabel= document.getElementById("nameLabel");
            var passLabel=document.getElementById("labelForPass");
            var passMatchLabel=document.getElementById("labelForPassMatch");
            var eMailLabel=document.getElementById("eMailLabel");
            if(firstName.length<2 || secondName.length<2) {nameLabel.style.display=""; return;}
            if(pass.length<6) {passLabel.style.visibility="visible";return;}
            if(pass!=pass2) {passMatchLabel.style.visibility="visible"; return;}
            if(!re.test(eMail)) {eMailLabel.style.visibility="visible"; return;}
            document.forms['form'].submit();
        }
    </script>
</head>

<body class="">
<iframe src="/top" name="usersFrame" width="100%" height="90px" scrolling="no"
        border="0px"></iframe>

<div class="container">

   <%-- <div class="row">
        <div class="col-md-12 ">
            <ul class="lead nav nav-pills">
                <li class="">
                    <a href="/home" target="_top">Home</a>
                </li>
                <li>
                    <a href="/projects" target="_top">Projects</a>
                </li>
                <li class="pull-right">
                    <a href="/userinfo/1" target="_top">Users</a>
                </li>
            </ul>
        </div>
    </div>--%>

    <div class="container">

        <div class="row">
            <div class="col-md-12" draggable="true" style="">
                <div class="" draggable="true">
                    <div class="col-md-8 pull-left well well-lg">
                        <center><h2>Welcome to our company</h2>  <br>
                               <c:if test="${error=='unknow'}">
                               <span class="error">You can`t register on this link. Ask for another invite.</span>
                               </c:if>
                        </center>
                        <form:form action="/completeRegistration" modelAttribute="User" class="form-horizontal" role="form" name="form">
                        <div class="row">
                            <div class="form-group col-md-6">
                                <div class="col-sm-3">
                                    <label for="firstName" class="control-label">Name</label>
                                </div>
                                <div class="col-sm-8">
                                    <form:input path="firstName" class="form-control" id="firstName" />
                                </div>
                            </div>

                            <div class="form-group col-md-6">
                                <div class="col-sm-3">
                                    <label for="secondName" class="control-label">Surname</label>
                                </div>
                                <div class="col-sm-8">
                                    <form:input path="secondName" class="form-control" id="secondName" />
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <label id ="nameLabel" style="display:none;color:red;">Is it your real name?</label>
                            </div>
                        </div>

                            <div class="form-group">
                                <div class="col-sm-3">
                                    <label for="login" class="control-label">Login</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:input path="login" class="form-control" id="login" />
                                </div>
                                <div class="col-sm-4">
                                    <c:if test="${error=='login'}">
                                        <span class="error">Login is in use now</span>
                                    </c:if>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-3">
                                    <label for="password" class="control-label">Password</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:input path="password" class="form-control" id="password" />
                                </div>
                                <div class="col-sm-3">
                                    <label id ="labelForPass" style="visibility: hidden;color:red;">Bad password</label>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-3">
                                    <label for="password2" class="control-label">Match password</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:input path="" class="form-control" id="password2" />
                                </div>
                                <div class="col-sm-3">
                                   <label id ="labelForPassMatch" style="visibility: hidden;color:red;">Passwords don`t match</label>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-3">
                                    <label for="eMail" class="control-label">E-mail</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:input path="eMail" class="form-control" id="eMail" />
                                </div>
                                <div class="col-sm-3">
                                    <label id ="eMailLabel" style="visibility: hidden;color:red;">Incorrect e-mail</label>
                                </div>
                            </div>
                            <c:if test="${regCode.manager}">
                                <div class="form-group">
                                    <div class="col-sm-3">
                                        <label class="control-label">Is manager</label>
                                    </div>
                                    <div class="col-sm-6">
                                        <font color="green">Yes</font>

                                    </div>
                                </div>
                            </c:if>
                            <c:choose>
                            <c:when test="${regCode.manager}">
                                <input type="hidden" name="role" value="ROLE_MODERATOR"></c:when>
                                <c:otherwise><input type="hidden" name="role" value="ROLE_USER"></c:otherwise></c:choose>

                            <input type="hidden" name="registrationCode" value="${regCode.code}">
                            <a class="btn btn-primary btn-large" onclick="validate();">Save</a>
                        </form:form>
                    </div>
                    <iframe src="/getUsers/0" style="height:600px;border:0px; margin: 20px 0px 0px 0px" />


                </div>
            </div>
        </div>
    </div>
</body>


</html>