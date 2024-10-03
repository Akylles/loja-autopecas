<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Login</h2>
<form action="usuarios" method="post">
    Login: <input type="text" name="login" required/><br/>
    Senha: <input type="password" name="senha" required/><br/>
    <input type="submit" value="Entrar"/>
</form>
</body>
</html>
