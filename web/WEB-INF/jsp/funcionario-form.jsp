<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Formulário de Funcionário</title>
</head>
<body>
<h2>${funcionario == null ? 'Cadastrar Funcionário' : 'Editar Funcionário'}</h2>
<form action="funcionarios" method="post">
    <input type="hidden" name="id" value="${funcionario != null ? funcionario.id : ''}"/>
    Nome: <input type="text" name="nome" value="${funcionario != null ? funcionario.nome : ''}" required/><br/>
    Cargo: <input type="text" name="cargo" value="${funcionario != null ? funcionario.cargo : ''}" required/><br/>
    Telefone: <input type="text" name="telefone" value="${funcionario != null ? funcionario.telefone : ''}" required/><br/>
    <input type="submit" value="${funcionario == null ? 'Cadastrar' : 'Atualizar'}"/>
</form>
<a href="funcionarios">Voltar</a>
</body>
</html>
