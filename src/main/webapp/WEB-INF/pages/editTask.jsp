<%@ page import="az.mecid.models.Project" %>
<%@ page import="az.mecid.enums.Access" %>
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

        function  addToManagedUsers(userId){

            var list=document.getElementById("listOfUsers");
            var li=document.getElementById(userId);
            var pre=document.createElement('pre');
       var node=document.createTextNode(li.firstChild.text+'   ');    //   Забрать
            var select=document.createElement('select');
            var imgMinus=document.createElement("img");
            imgMinus.align="right";
            imgMinus.src="data/minus.png";
            imgMinus.name=userId;
            select.options[0]=new Option("Owner","<%=Access.Owner%>");
            select.options[1]=new Option("Observer","<%=Access.Owner%>");

            pre.appendChild(node);// Забрать
            pre.appendChild(select);
            pre.appendChild(imgMinus)

            list.appendChild(pre);
            imgMinus.onclick=returnToUl;
            removeFromUl(userId);
        }
        function removeFromUl(userId){
            var li=document.getElementById(userId);
     //   var node=document.createTextNode(li.firstChild.text+'   ');
            var bdg=document.getElementById("badge/"+userId);
            li.firstElementChild.style.visibility="hidden";
          bdg.style.visibility="hidden";
        }
        function returnToUl(){
            var li=document.getElementById(this.name);
            var bdg=document.getElementById("badge/"+this.name);
            li.firstElementChild.style.visibility="visible";
            bdg.style.visibility="visible";
            var list=document.getElementById("listOfUsers");
            list.removeChild(this.parentNode);
        }

    </script>
</head>

<body class="">
<iframe src="/top" width="100%" height="170px" scrolling="no" border="0px"></iframe>
<div class="container">
    <div class="row">
        <div class="col-md-12" draggable="true" style="">
            <font size="+3">Creating task</font>
            <div class="row" draggable="true">
                <div class="col-md-8 pull-left">
                    <br>
                    <div class="well" style="border-radius:10px">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="title" class="control-label">Title</label>
                                </div>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="title" <c:if test="${task.id >0}"> placeholder="${task.title}"</c:if> placeholder="Title" > <!-- Сюди треба поставити перевірку чи можна вводити такі дані-->
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="description" class="control-label">Description</label>
                                </div>
                                <div class="col-sm-6">
                                    <textarea class="form-control" rows="8" id="description"><c:if test="${task.id >0}"> ${task.description}</c:if> </textarea>
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="manager" class="control-label">Add users</label>
                                </div>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="manager"  <c:if test="${project.id >0}"> placeholder="${project.manager.login}"</c:if>placeholder="Manager">
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="description" class="control-label">Users in task</label>
                                </div>
                                <div class="col-sm-6" id="listOfUsers">
                                </div>
                            </div>
                            <hr>
                            <a class="btn btn-primary btn-large">Save</a>
                        </form>
                    </div>
                </div>
                <div class="col-md-3 pull-left" id="block">
                   <br/>
                    <ul class="lead list-group" id="ulUsers">
                            <c:forEach items="${userList}" var="user">
                                <li class="list-group-item" id="${user.id}"><a href="/userinfo/${user.id}"  target="_top" >${user.login}</a>
                                    <span class="badge"  id="badge/${user.id}"><img src="data/plus.png" onclick="addToManagedUsers(${user.id})"/></span>
                                </li>
                            </c:forEach>

                        </ul>

                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>
