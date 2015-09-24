<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
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

            <a class="navbar-brand" href="/projects/${project.id}" target="_top">${project.name}</a>
            <c:if test="${empty project.name}"><a class="navbar-brand" href="/" target="_top">WayMine</a></c:if>

        </div>
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav navbar-right" style=""></ul>
            <div class="row text-left" style="" draggable="true">
                <div class="col-md-4 col-md-offset-5" style="" draggable="true">
                    <div class="col-md-4 col-md-offset-5 text-right" draggable="true">
                     <br>Hello, <b><u>${login}</u></b>
                    </div>
                    <div class="col-md-3 text-center" draggable="true">
                        <br>
                         <div class="form-group">
                           <div class="col-sm-2">
                               <a href="/logout" target="_top" class="btn btn-info btn-sm">Logout</a>
                            </div>
                        </div>
                    </div>
                </div>

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
                <a href="/form/createTask/${project.id}" target="_top">New task</a>
            </li>

        </ul>
    </div>
</div>

</body>
</html>