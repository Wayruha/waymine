<%@ page import="az.mecid.models.Task" %>
<%@ page import="az.mecid.enums.Access" %>
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
            var node=document.createTextNode(li.firstChild.text+'   ');    //   Забрать
            var select=document.createElement('select');
            var imgMinus=document.createElement("img");
            imgMinus.align="right";
            imgMinus.src="data/minus.png";
            imgMinus.name=userId;
            imgMinus.onclick=returnToUl;
            select.options[0]=new Option("Owner","<%=Access.Owner%>");
            select.options[1]=new Option("Observer","<%=Access.Owner%>");
            pre.appendChild(node);// Забрать
            pre.appendChild(select);
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
            var usersString="", accessString="";
            Array.prototype.forEach.call(PREs,function(el) {usersString+=el.childNodes[0].nodeValue.substr(0,el.childNodes[0].nodeValue.length-4)+"--";accessString+=el.childNodes[1].selectedOptions.item(0).textContent.trim()+"--"; });

            document.getElementById("userList").value=usersString;
            document.getElementById("accessList").value=accessString;
        }

        function loadUserAccess(){

            var list=document.getElementById("listOfUsers");
            var ulUsers=document.getElementById("ulUsers");
            var  PREs=list.getElementsByTagName("pre");
            Array.prototype.forEach.call(PREs,function(el) {
                var userId=el.childNodes[3].name;                          // text+=el.childNodes[0].nodeValue;
                el.childNodes[3].onclick=returnToUl;
                var li=document.getElementById(userId);
                var bdg=document.getElementById("badge/"+userId);
                li.firstElementChild.style.visibility="hidden";
                bdg.style.visibility="hidden";
            });
        }
    </script>
</head>

<body onload="loadUserAccess();">
<iframe src="/topFromProj?project=${projectId}" width="100%" height="90px" scrolling="no" border="0px"></iframe>
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
                <li class="pull-right" id="taskButt">
                    <a href="/form/createTask/${projectId}" target="_top">New task</a>
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
    <div class="row">
        <div class="col-md-12" draggable="true" style="">
            <table width="85%"> <tr>
            <td><font size="+3">Creating task</font></td>
                <td style="text-align: right"><font size="+3">Add users</font></td>
            </tr></table>
            <div class="row" draggable="true">
                <div class="col-md-8 pull-left">
                    <br>
                    <div class="well" style="border-radius:10px">
                        <form:form action="/form/saveTask?project=${projectId}" modelAttribute="TaskForm" class="form-horizontal" role="form" name="form">
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="title" class="control-label">Title</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:input path="title" class="form-control" id="title" />
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
                                    <form:textarea path="description" class="form-control" id="description" rows="8"/>
                            </div>
                            </div>
                            <hr>

                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="description" class="control-label">Add users</label>
                                </div>
                                <div class="col-sm-6" id="listOfUsers">
                                     <c:forEach items="${t_uList}" var="t_u">
                                         <pre>${t_u.user.login}&nbsp;&nbsp;&nbsp;&nbsp;<select>
                                             <option value="<%=Access.Owner%>" title="Owner"<c:if test="${t_u.access=='Owner'}">selected="selected" </c:if>>Owner </option>
                                             <option value="<%=Access.Observer%>" title="Observer" <c:if test="${t_u.access=='Observer'}">selected="selected"</c:if>>Observer</option>
                                             </select>   <img src="data/minus.png" align="right" name="${t_u.user.id}" />
                                         </pre>

                                     </c:forEach>
                                </div>
                            </div>
                            <hr>

                           <%-- <c:if test="${error=='true'}">
                                <font color="red">
                                    The error was happen. Please, try again
                                </font>
                            </c:if>--%>
                            <input type="hidden" id="userList" name="userList">
                            <input type="hidden" id="accessList" name="accessList">
                            <input type="hidden" id="login" name="creator" value="${login}">
                            <input type="hidden" id="id" name="id" value="${taskId}">
                            <input type="hidden" id="editing" path="editing" value="editing" name="editing">
   <!--- -->               <a class="btn btn-primary btn-large" onclick="convertUserList();document.forms['form'].submit();">Save</a>
                            <c:if test="${editing}"><input type="hidden" value="editing" path="editing"></c:if>
                        </form:form>      <c:if test="${error=='valid'}">
                        <font color="red">
                            Не правильно заповнені поля
                        </font>
                    </c:if>
                    </div>
                </div>
                <div class="col-md-3 pull-left" id="block">
                   <br/>
                    <ul class="lead list-group" id="ulUsers">

                            <c:forEach items="${t_uList}" var="t_u">
                                    <li class="list-group-item" id="${t_u.user.id}"><a href="/userinfo/${t_u.user.id}"  target="_top" >${t_u.user.login}</a>
                                    <span class="badge"  id="badge/${t_u.user.id}"><img src="data/plus.png" onclick="addToManagedUsers(${t_u.user.id})"/></span>
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
