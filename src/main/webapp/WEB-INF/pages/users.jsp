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
                <c:choose>
                    <c:when test="${not empty usersList}">
                        <c:forEach items="${usersList}" var="user">
                            <li class="list-group-item"><a href="/userinfo/${user.id}" id="${user.id}"
                                                           target="_top">${user.login}</a>
                      <span class="badge">
                      <c:choose>
                          <c:when test="${user.role=='ROLE_MODERATOR'}">
                              <img src="data/manager2.svg" alt="Manager">
                          </c:when>
                          <c:otherwise>
                              <img src="data/employee.png" alt="Employee">
                          </c:otherwise>
                      </c:choose>
                      </span>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${t_uList}" var="t_u">
                            <li class="list-group-item"><a href="/userinfo/${t_u.user.id}" id="${t_u.user.id}"
                                                           target="_top">${t_u.user.login}</a>
                      <span class="badge">
                      <c:choose>
                          <c:when test="${t_u.access=='Owner'}">
                              <img src="data/userOwner.png" alt="Owner">
                          </c:when>
                          <c:otherwise>
                              <img src="data/userObserver.png" alt="Observer">
                          </c:otherwise>
                      </c:choose>
                      </span>
                            </li>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</div>



</body>
</html>