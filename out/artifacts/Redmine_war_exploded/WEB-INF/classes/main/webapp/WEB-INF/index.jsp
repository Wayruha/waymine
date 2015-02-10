<%--
  Created by IntelliJ IDEA.
  User: mega
  Date: 05.03.2010
  Time: 0:01:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title>
    <style type="text/css">
        #top {
            width: 90%;
            height: 10%;
            background: #ccc;
            padding: 5px,20px,0,10px;
            border: solid 1px black;
            float: left;
            font-family: monospace;
            font-size:46px;
            text-align: center;
        }
        #menu {
            width: 90%;
            height: 5%;
            background: #fc0;
            border: solid 1px black;
            float: left;
            top: 40px;
        }
        #content {
            background: yellowgreen;
            padding-left:20px;
            width: 70%;
            border: 2px double;
        }
        #leftThing {
            position: relative;
            background: cyan;

        }
    </style>
</head>
<body>

<div id="top"><font color="blue">Way</font><font color="red">Mine</font></div>
<div id="menu">
    <table>
        <tr>
            <td width="300" align="center">Меню раз</td>
            <td width="300" align="center">Меню два</td>
            <td width="300" align="center">Меню три</td>
        </tr>
    </table>

</div>
<div id="content">
    wadawd
</div>
<div id="leftThing"></div>



</body>
</html>