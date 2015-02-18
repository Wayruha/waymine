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
        function func(){
            var taskLi=document.getElementById("taskButt");
           // alert(window.frames.pop().location.pathname);
            if(${isProjectPage==true}) taskLi.className="";else taskLi.className="hidden";

        }
    </script>
</head>

<body onload="func()">
<div class="navbar navbar-default navbar-static-top" style="min-height: 90px;">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand" href="index" target="_top">${request.projectName}</a>

        </div>
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav navbar-right" style=""></ul>
            <div class="row text-left" style="" draggable="true">
                <div class="col-md-4 col-md-offset-5" style="" draggable="true">
                    <div class="col-md-12 text-center" draggable="true">
                        <form class="form-vertical" role="form" style="">
                            <div class="form-group">
                                <div class="col-sm-5">
                                    <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
                                </div>
                            </div>
                            <div class="form-group" draggable="true">
                                <div class="col-sm-5">
                                    <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
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
            <li class="">
                <a href="/form/editProject/0" target="_top">New project</a>
            </li>
           <li class="hidden" id="taskButt">
                <a href="/form/editTask/0" target="_top">New task</a>
            </li>

        </ul>
    </div>
</div>

</body>
</html>