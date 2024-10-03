<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Formulário de Veículo</title>
</head>
<body>
<h2>${veiculo == null ? 'Cadastrar Veículo' : 'Editar Veículo'}</h2>
<form action="veiculos" method="post">
    <input type="hidden" name="id" value="${veiculo != null ? veiculo.id : ''}"/>
    Modelo: <input type="text" name="modelo" value="${veiculo != null ? veiculo.modelo : ''}" required/><br/>
    Placa: <input type="text" name="placa" value="${veiculo != null ? veiculo.placa : ''}" required/><br/>
    Ano: <input type="number" name="ano" value="${veiculo != null ? veiculo.ano : ''}" required/><br/>
    <input type="submit" value="${veiculo == null ? 'Cadastrar' : 'Atualizar'}"/>
</form>
<a href="veiculos">Voltar</a>
</body>
</html>
