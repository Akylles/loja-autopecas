package br.com.loja_autopecas.dao;

import br.com.loja_autopecas.entidades.Peca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {
    private Connection conn;

    public PecaDAO(Connection conn) {
        this.conn = conn;
    }

    // Método para adicionar peça
    public void adicionarPeca(Peca peca) throws SQLException {
        String sql = "INSERT INTO pecas (nome, preco) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getPreco());
            stmt.executeUpdate();
        }
    }

    // Método para atualizar peça
    public void atualizarPeca(Peca peca) throws SQLException {
        String sql = "UPDATE pecas SET nome = ?, preco = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getPreco());
            stmt.setInt(3, peca.getId());
            stmt.executeUpdate();
        }
    }

    // Método para remover peça
    public void removerPeca(int id) throws SQLException {
        String sql = "DELETE FROM pecas WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Método para buscar uma peça por ID
    public Peca buscarPecaPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pecas WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Peca peca = new Peca();
                    peca.setId(rs.getInt("id"));
                    peca.setNome(rs.getString("nome"));
                    peca.setPreco(rs.getDouble("preco"));
                    return peca;
                }
            }
        }
        return null;
    }

    // Método para listar todas as peças
    public List<Peca> listarTodasPecas() throws SQLException {
        String sql = "SELECT * FROM pecas";
        List<Peca> pecas = new ArrayList<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Peca peca = new Peca();
                peca.setId(rs.getInt("id"));
                peca.setNome(rs.getString("nome"));
                peca.setPreco(rs.getDouble("preco"));
                pecas.add(peca);
            }
        }
        return pecas;
    }
}
