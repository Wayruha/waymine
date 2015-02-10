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
    </script>
</head>

<body class="">
<iframe src="/top" width="100%" height="170px" scrolling="no" border="0px"></iframe>
<div class="container">
    <div class="row">
        <div class="col-md-12" draggable="true" style="">
            <div class="" draggable="true">
                <div class="col-md-10 pull-left">
                    <br>
                    <font size="+3">Creating project</font>
                    <div class="well" style="border-radius:10px">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="title" class="control-label">Title</label>
                                </div>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="title" <c:if test="${project.id >0}"> placeholder="${project.name}"</c:if> placeholder="Title" > <!-- Сюди треба поставити перевірку чи можна вводити такі дані-->
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="description" class="control-label">Description</label>
                                </div>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="description" <c:if test="${project.id >0}"> placeholder="${project.description}"</c:if> placeholder="Description">
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="manager" class="control-label">Add manager</label>
                                </div>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="manager"  <c:if test="${project.id >0}"> placeholder="${project.manager.login}"</c:if>placeholder="Manager">
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <label for="description" class="control-label">Type</label>
                                </div>
                                <div class="col-sm-6">
                                    <input type=radio name="type" value="public" <c:if test="${project.type=='Public'}">checked</c:if>>Public &nbsp;&nbsp;&nbsp;
                                    <input type=radio name="type" value="private" <c:if test="${project.type=='Private'}">checked</c:if>>Private project
                                </div>
                            </div>
                            <hr>
                            <a class="btn btn-primary btn-large">Save</a>
                        </form>
                     </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>
