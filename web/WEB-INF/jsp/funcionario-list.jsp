<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Funcionários</title>
</head>
<body>
<h2>Lista de Funcionários</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Cargo</th>
        <th>Telefone</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="funcionario" items="${listaFuncionarios}">
        <tr>
            <td>${funcionario.id}</td>
            <td>${funcionario.nome}</td>
            <td>${funcionario.cargo}</td>
            <td>${funcionario.telefone}</td>
            <td>
                <a href="funcionarios?action=edit&id=${funcionario.id}">Editar</a>
                <a href="funcionarios?action=delete&id=${funcionario.id}">Excluir</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="funcionarios?action=new">Cadastrar Novo Funcionário</a>
</body>
</html>
