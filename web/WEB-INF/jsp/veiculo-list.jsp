<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Veículos</title>
</head>
<body>
<h2>Lista de Veículos</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Modelo</th>
        <th>Placa</th>
        <th>Ano</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="veiculo" items="${listaVeiculos}">
        <tr>
            <td>${veiculo.id}</td>
            <td>${veiculo.modelo}</td>
            <td>${veiculo.placa}</td>
            <td>${veiculo.ano}</td>
            <td>
                <a href="veiculos?action=edit&id=${veiculo.id}">Editar</a>
                <a href="veiculos?action=delete&id=${veiculo.id}">Excluir</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="veiculos?action=new">Cadastrar Novo Veículo</a>
</body>
</html>
