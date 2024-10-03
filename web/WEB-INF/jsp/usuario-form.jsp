<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Formulário de Usuário</title>
</head>
<body>
<h2>${usuario == null ? 'Cadastrar Usuário' : 'Editar Usuário'}</h2>
<form action="usuarios" method="post">
    <input type="hidden" name="id" value="${usuario != null ? usuario.id : ''}"/>
    Login: <input type="text" name="login" value="${usuario != null ? usuario.login : ''}" required/><br/>
    Senha: <input type="password" name="senha" value="${usuario != null ? usuario.senha : ''}" required/><br/>
    <input type="submit" value="${usuario == null ? 'Cadastrar' : 'Atualizar'}"/>
</form>
<a href="usuarios">Voltar</a>
</body>
</html>
