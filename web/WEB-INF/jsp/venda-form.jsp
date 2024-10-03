<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Formulário de Venda</title>
</head>
<body>
<h2>${venda == null ? 'Cadastrar Venda' : 'Editar Venda'}</h2>
<form action="vendas" method="post">
    <input type="hidden" name="id" value="${venda != null ? venda.id : ''}"/>
    Cliente ID: <input type="text" name="clienteId" value="${venda != null ? venda.clienteId : ''}" required/><br/>
    Data da Venda: <input type="date" name="dataVenda" value="${venda != null ? venda.dataVenda : ''}" required/><br/>
    Total: <input type="text" name="total" value="${venda != null ? venda.total : ''}" required/><br/>
    <input type="submit" value="${venda == null ? 'Cadastrar' : 'Atualizar'}"/>
</form>
<a href="vendas">Voltar</a>
</body>
</html>
