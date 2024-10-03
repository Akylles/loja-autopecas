<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Peças</title>
</head>
<body>
<h2>Lista de Peças</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Preço</th>
        <th>Estoque</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="peca" items="${listaPecas}">
        <tr>
            <td>${peca.id}</td>
            <td>${peca.nome}</td>
            <td>${peca.preco}</td>
            <td>${peca.estoque}</td>
            <td>
                <a href="pecas?action=edit&id=${peca.id}">Editar</a>
                <a href="pecas?action=delete&id=${peca.id}">Excluir</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="pecas?action=new">Cadastrar Nova Peça</a>
</body>
</html>
