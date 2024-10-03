<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Formulário de Serviço</title>
</head>
<body>
<h2>${servico == null ? 'Cadastrar Serviço' : 'Editar Serviço'}</h2>
<form action="servicos" method="post">
    <input type="hidden" name="id" value="${servico != null ? servico.id : ''}"/>
    Nome: <input type="text" name="nome" value="${servico != null ? servico.nome : ''}" required/><br/>
    Preço: <input type="text" name="preco" value="${servico != null ? servico.preco : ''}" required/><br/>
    Descrição: <input type="text" name="descricao" value="${servico != null ? servico.descricao : ''}" required/><br/>
    <input type="submit" value="${servico == null ? 'Cadastrar' : 'Atualizar'}"/>
</form>
<a href="servicos">Voltar</a>
</body>
</html>
