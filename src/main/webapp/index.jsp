<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>WayMine-projects managment</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
          rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>

<body class="">
<%--<iframe src="top" width="100%" height="170px" scrolling="no" border="0px"></iframe>--%>
<div class="navbar navbar-default navbar-static-top" style="min-height: 90px;">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand">WayMine</a>

        </div>
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav navbar-right" style=""></ul>
            <div class="row text-left" style="" draggable="true">
             <%--   <c:when test="${pageContext.session.valueNames}"></c:when>--%>
                <div class="col-md-4 col-md-offset-5" style="" draggable="true">
                    <c:if test="${not empty param.error}">
                        <font color="red"> <spring:message code="label.loginerror" />
                            : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} </font>
                    </c:if>
                    <div class="col-md-12 text-center" draggable="true">
                        <form class="form-vertical" role="form" method="POST"  action="<c:url value="/j_spring_security_check" />">
                            <div class="form-group">
                                <div class="col-sm-5">
                                    <input type="login" class="form-control" id="inputEmail3" placeholder="Login" name="j_username">
                                </div>
                            </div>
                            <div class="form-group" draggable="true">
                                <div class="col-sm-5">
                                    <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name="j_password">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <button type="submit" class="btn btn-default">Sign in</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <a class="btn btn-info col-sm-1 pull-right" draggable="true">Admin</a>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12 col-md-offset-2" >
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
        </ul>
    </div>
</div>

<div class="container">

    <div class="row">
        <div class="col-md-12" draggable="true" style="">
            <div class="" draggable="true">
                <div class="col-md-8 pull-left">
                    <h2>A lot of text</h2>  <br>

                    <div class="well">

                      Прівєтіки . Вітаємо на нашому сайті


                    </div>

                </div>
             <%--   <iframe src="/getUsers/all" style="border:0px; margin: 20px 0px 0px 0px" />--%>


            </div>
        </div>
    </div>
</div>
</body>


</html>