<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Serviços</title>
</head>
<body>
<h2>Lista de Serviços</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Preço</th>
        <th>Descrição</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="servico" items="${listaServicos}">
        <tr>
            <td>${servico.id}</td>
            <td>${servico.nome}</td>
            <td>${servico.preco}</td>
            <td>${servico.descricao}</td>
            <td>
                <a href="servicos?action=edit&id=${servico.id}">Editar</a>
                <a href="servicos?action=delete&id=${servico.id}">Excluir</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="servicos?action=new">Cadastrar Novo Serviço</a>
</body>
</html>
