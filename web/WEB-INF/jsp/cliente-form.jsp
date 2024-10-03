<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Formulário de Cliente</title>
</head>
<body>
<h2>${cliente == null ? 'Cadastrar Cliente' : 'Editar Cliente'}</h2>
<form action="clientes" method="post">
    <input type="hidden" name="id" value="${cliente != null ? cliente.id : ''}"/>
    Nome: <input type="text" name="nome" value="${cliente != null ? cliente.nome : ''}" required/><br/>
    CPF: <input type="text" name="cpf" value="${cliente != null ? cliente.cpf : ''}" required/><br/>
    Telefone: <input type="text" name="telefone" value="${cliente != null ? cliente.telefone : ''}" required/><br/>
    Endereço: <input type="text" name="endereco" value="${cliente != null ? cliente.endereco : ''}" required/><br/>
    <input type="submit" value="${cliente == null ? 'Cadastrar' : 'Atualizar'}"/>
</form>
<a href="clientes">Voltar</a>
</body>
</html>
