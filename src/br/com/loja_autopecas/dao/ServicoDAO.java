package br.com.loja_autopecas.dao;

import br.com.loja_autopecas.entidades.Servico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {
    private Connection conn;

    public ServicoDAO(Connection conn) {
        this.conn = conn;
    }

    // Método para adicionar serviço
    public void adicionarServico(Servico servico) throws SQLException {
        String sql = "INSERT INTO servicos (descricao, valor) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getValor());
            stmt.executeUpdate();
        }
    }

    // Método para atualizar serviço
    public void atualizarServico(Servico servico) throws SQLException {
        String sql = "UPDATE servicos SET descricao = ?, valor = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getValor());
            stmt.setInt(3, servico.getId());
            stmt.executeUpdate();
        }
    }

    // Método para remover serviço
    public void removerServico(int id) throws SQLException {
        String sql = "DELETE FROM servicos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Método para buscar um serviço por ID
    public Servico buscarServicoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM servicos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Servico servico = new Servico();
                    servico.setId(rs.getInt("id"));
                    servico.setDescricao(rs.getString("descricao"));
                    servico.setValor(rs.getDouble("valor"));
                    return servico;
                }
