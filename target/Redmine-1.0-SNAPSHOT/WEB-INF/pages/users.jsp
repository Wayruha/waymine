<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
</head>
<body >
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <ul class="lead list-group">
                <c:forEach items="${usersList}" var="user">
                  <li class="list-group-item"><a href="/userinfo/${user.id}" id="${user.id}" target="_top" >${user.login}</a>
                      <span class="badge">42</span>
                  </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>



</body>
</html>