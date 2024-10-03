<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Usuários</title>
</head>
<body>
<h2>Lista de Usuários</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Login</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="usuario" items="${listaUsuarios}">
        <tr>
            <td>${usuario.id}</td>
            <td>${usuario.login}</td>
            <td>
                <a href="usuarios?action=edit&id=${usuario.id}">Editar</a>
                <a href="usuarios?action=delete&id=${usuario.id}">Excluir</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="usuarios?action=new">Cadastrar Novo Usuário</a>
</body>
</html>
