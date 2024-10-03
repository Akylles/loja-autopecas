<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Formulário de Peça</title>
</head>
<body>
<h2>${peca == null ? 'Cadastrar Peça' : 'Editar Peça'}</h2>
<form action="pecas" method="post">
    <input type="hidden" name="id" value="${peca != null ? peca.id : ''}"/>
    Nome: <input type="text" name="nome" value="${peca != null ? peca.nome : ''}" required/><br/>
    Preço: <input type="text" name="preco" value="${peca != null ? peca.preco : ''}" required/><br/>
    Estoque: <input type="number" name="estoque" value="${peca != null ? peca.estoque : ''}" required/><br/>
    <input type="submit" value="${peca == null ? 'Cadastrar' : 'Atualizar'}"/>
</form>
<a href="pecas">Voltar</a>
</body>
</html>
