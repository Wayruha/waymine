<%@ page import="az.mecid.models.Project" %>
<%@ page import="az.mecid.forms.ProjectForm" %>
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
    </script>
</head>

<body class="">
<iframe src="/top" width="100%" height="90px" scrolling="no" border="0px"></iframe>
<div class="container">
    <div class="row">
        <div class="col-md-12" >
            <ul class="lead nav nav-pills">
                <li class="">
                    <a href="/home" target="_top">Home</a>
                </li>
                <li class="">
                    <a href="/projects" target="_top">Projects</a>
                </li>
                <li class="">
                    <a href="/userinfo/1" target="_top">Users</a>
                </li>
                <li class="active">
                    <a href="/form/createProject" target="_top">New project</a>
                </li>

                <li class="hidden" id="taskButt">
                    <a href="/form/createTask/${task.project.id}" target="_top">New task</a>
                </li>

            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12" draggable="true" style="">
            <div class="" draggable="true">
                <div class="col-md-10 pull-left">
                    <br>
                    <font size="+3">Creating project</font>
                    <div class="well" style="border-radius:10px">
                        <c:if test="${error=='valid'}">
                            <font color="red">
                                Не вірно заповнені поля
                            </font>
                        </c:if>
                     <form:form action="/form/saveProject" modelAttribute="ProjectForm" class="form-horizontal" role="form" name="form">
                       <!-- <form class="form-horizontal" role="form" action="/form/saveProjForm" method="GET" name="form">-->
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="title" class="control-label">Title</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:input path="title" class="form-control" id="title" placeholder="${project.title}"/>
                                  <!-- Сюди треба поставити перевірку чи можна вводити такі дані-->
                                </div>
                                <div class="col-sm-4">
                                    <c:if test="${error=='title'}">
                                        <font color="red">
                                            This title is already in use
                                        </font>
                                    </c:if>
                                    <form:errors path="title">asdasdasdasdasds</form:errors>
                                </div>
                            </div>

                            <hr>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="description" class="control-label">Description</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:textarea path="description" class="form-control" id="description" rows="8" placeholder="${project.description}"/>

                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="manager" class="control-label">Add manager</label>
                                </div>
                                <div class="col-sm-6">
                                    <form:input path="manager" class="form-control" id="manager"  placeholder="${project.manager}"/>
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

                            <a class="btn btn-primary btn-large" onclick="document.forms['form'].submit();">Save</a>
                            <c:if test="${editing}"><input type="hidden" value="editing" path="editing"></c:if>
                        <!--</form>-->
                         </form:form>
                     </div>


                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>
