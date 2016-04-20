<%@ page import="net.slipp.week6.beans.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <title>Title</title>
</head>
<body>
<div class="section">
    <h3>member by session</h3>
    <div><%= new java.util.Date()%></div>
    <div>${member}</div>
</div>
</body>
</html>
