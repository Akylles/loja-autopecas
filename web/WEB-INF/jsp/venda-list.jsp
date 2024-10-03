<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Vendas</title>
</head>
<body>
<h2>Lista de Vendas</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Cliente ID</th>
        <th>Data da Venda</th>
        <th>Total</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="venda" items="${listaVendas}">
        <tr>
            <td>${venda.id}</td>
            <td>${venda.clienteId}</td>
            <td>${venda.dataVenda}</td>
            <td>${venda.total}</td>
            <td>
                <a href="vendas?action=edit&id=${venda.id}">Editar</a>
                <a href="vendas?action=delete&id=${venda.id}">Excluir</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="vendas?action=new">Cadastrar Nova Venda</a>
</body>
</html>
