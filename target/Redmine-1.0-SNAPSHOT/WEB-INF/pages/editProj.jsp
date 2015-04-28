<%@ page import="az.mecid.models.Task" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            var node=document.createTextNode(li.firstChild.text+'    ');    //   Забрать

            var imgMinus=document.createElement("img");
            imgMinus.align="right";
            imgMinus.src="data/minus.png";
            imgMinus.name=userId;
            imgMinus.onclick=returnToUl;
            pre.appendChild(node);// Забрать
            pre.appendChild(imgMinus)
            list.appendChild(pre);
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
        function convertUserList(){
            var list=document.getElementById("listOfUsers");
            var  PREs=list.getElementsByTagName("pre");
            var usersString="";
            Array.prototype.forEach.call(PREs,function(el) {usersString+=el.childNodes[0].nodeValue.substr(0,el.childNodes[0].nodeValue.length-3)+"--";});         // чого 3? так треба

            document.getElementById("userList").value=usersString;

        }
        function loadUserAccess(){
            var list=document.getElementById("listOfUsers");
            var ulUsers=document.getElementById("ulUsers");
            var  PREs=list.getElementsByTagName("pre");
            Array.prototype.forEach.call(PREs,function(el) {
                var userId=el.childNodes[1].name;// text+=el.childNodes[0].nodeValue;
                el.childNodes[1].onclick=returnToUl;
                var li=document.getElementById(userId);
                var bdg=document.getElementById("badge/"+userId);
                li.firstElementChild.style.visibility="hidden";
                bdg.style.visibility="hidden";
            });
        }

    </script>
</head>

<body class="" onload="loadUserAccess()">
<iframe src="/top" width="100%" height="90px" scrolling="no" border="0px"></iframe>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <ul class="lead nav nav-pills">
                <li class="">
                    <a href="/home" target="_top">Home</a>
                </li>
                <li class="">
                    <a href="/projects" target="_top">Projects</a>
                </li>
                <li class="pull-right">
                    <a href="/userinfo/1" target="_top">Users</a>
                </li>

                <li class="hidden" id="taskButt">
                    <a href="/form/createTask/${task.project.id}" target="_top">New task</a>
                </li>

            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12" draggable="true" style="">
            <table width="85%">
                <tr>
                    <c:if test="${project.editing==false}"><td><font size="+3">Creating project</font></td>
                        <td style="text-align: right"><font size="+3">Add users</font></td>
                    </c:if>
                    <c:if test="${project.editing==true}"><td><font size="+3">Creating project</font></td>
                        <td style="text-align: right"><font size="+3">Users in project</font></td>
                    </c:if>

                </tr>
            </table>
            <div class="row" draggable="true">

                <div class="col-md-8 pull-left">
                    <br>
                    <div class="well" style="border-radius:10px">
                        <c:if test="${error=='valid'}">
                            <font color="red">
                                Не вірно заповнені поля
                            </font>
                        </c:if>
                        <form:form action="/form/saveProject" modelAttribute="ProjectForm" class="form-horizontal" role="form" name="form">

                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="title" class="control-label">Title</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:input path="title" class="form-control" id="title" value="${project.title}"/>
                                    <!-- Сюди треба поставити перевірку чи можна вводити такі дані-->
                                </div>
                                <div class="col-sm-4">
                                    <c:if test="${error=='title'}">
                                        <font color="red">
                                            This title is already in use
                                        </font>
                                    </c:if>
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="description" class="control-label">Description</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:textarea path="description" class="form-control" id="description" rows="8" value="${project.description}"/>
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="manager" class="control-label">Set manager</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:input path="manager" class="form-control" id="manager"  value="${project.manager}"/>
                                </div>
                                <div class="col-sm-4">
                                    <c:if test="${error=='manager'}">
                                        <font color="red">
                                            This user did not find
                                        </font>
                                    </c:if>
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="description" class="control-label">Type</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:radiobuttons path="type" items="${radioTypeList}" style="margin:0px 10px"/>
                                </div>
                             </div>
                            <hr>
                                <div class="form-group">
                                <div class="col-sm-2">
                                <label class="control-label">Involved users</label>
                                </div>
                                    <div class="col-sm-6" id="listOfUsers">
                                        <c:forEach items="${userListInProject}" var="user">
                                         <pre style="height:40px">${user.login}&nbsp;&nbsp;&nbsp;<img src="data/minus.png" height="20" align="right" name="${user.id}" />
                                         </pre>
                                        </c:forEach>
                                    </div>


                            </div>
                            <a class="btn btn-primary btn-large" onclick="convertUserList();document.forms['form'].submit();">Save</a>
                            <input type="hidden" name="id" value="${project.id}">
                            <input type="hidden" id="userList" name="userList">
                            <c:if test="${project.editing==true}"><input type="hidden" value="${true}" name="editing"></c:if>
                            <!--</form>-->
                        </form:form>
                    </div>
                </div>
                <div class="col-md-3 pull-left" id="block">
                    <br/>
                    <ul class="lead list-group" id="ulUsers">

                        <c:forEach items="${allUserList}" var="user">
                            <li class="list-group-item" id="${user.id}"><a href="/userinfo/${user.id}"
                                                                           target="_top">${user.login}</a>
                                    <span class="badge" id="badge/${user.id}"><img src="data/plus.png"
                                                                                   onclick="addToManagedUsers(${user.id})"/></span>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

</body>

</html>
