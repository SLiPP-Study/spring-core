<%@ page import="net.slipp.week6.beans.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <title>Title</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
          integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
</head>
<body>
<div class="section">

    <h4>${title}</h4>
    <table class="table table-condensed">
        <thead>
        <tr>
            <th>scope</th>
            <th>reference</th>
            <th>name</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>singleton</td>
            <td>${members.get("singleton")}</td>
            <td>${members.get("singleton").name}</td>
        </tr>
        <tr>
            <td>prototype</td>
            <td>${members.get("prototype")}</td>
            <td>${members.get("prototype").name}</td>
        </tr>
        <tr>
            <td>request</td>
            <td>${members.get("request")}</td>
            <td>${members.get("request").name}</td>
        </tr>
        <tr>
            <td>session</td>
            <td>${members.get("session")}</td>
            <td>${members.get("session").name}</td>
        </tr>
        <tr>
            <td>request(proxy)</td>
            <td>${members.get("outlier")}</td>
            <td>${members.get("outlier").name}</td>
        </tr>
        </tbody>
    </table>
    <div clas="well right">Current Time: <%= new java.util.Date()%>
    </div>
</div>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>
</body>
</html>
