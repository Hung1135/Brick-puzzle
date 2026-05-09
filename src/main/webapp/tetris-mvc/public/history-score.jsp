
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<table>

    <tr>
        <th>Score</th>
        <th>Level</th>
        <th>Lines</th>
        <th>Date</th>
    </tr>

    <c:forEach items="${scores}" var="s">

        <tr>

            <td>${s.score}</td>

            <td>${s.level}</td>

            <td>${s.linesCleared}</td>

            <td>${s.createdAt}</td>

        </tr>

    </c:forEach>

</table>
</body>
</html>
