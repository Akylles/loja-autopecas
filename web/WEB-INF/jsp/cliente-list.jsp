<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Clientes</title>
</head>
<body>
<h2>Lista de Clientes</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>CPF</th>
        <th>Telefone</th>
        <th>Endereço</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="cliente" items="${listaClientes}">
        <tr>
            <td>${cliente.id}</td>
            <td>${cliente.nome}</td>
            <td>${cliente.cpf}</td>
            <td>${cliente.telefone}</td>
            <td>${cliente.endereco}</td>
            <td>
                <a href="clientes?action=edit&id=${cliente.id}">Editar</a>
                <a href="clientes?action=delete&id=${cliente.id}">Excluir</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="clientes?action=new">Cadastrar Novo Cliente</a>
</body>
</html>
