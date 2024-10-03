package br.com.loja_autopecas.dao;

import br.com.loja_autopecas.entidades.Veiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    private Connection conn;

    public VeiculoDAO(Connection conn) {
        this.conn = conn;
    }

    // Método para adicionar veículo
    public void adicionarVeiculo(Veiculo veiculo) throws SQLException {
        String sql = "INSERT INTO veiculos (placa, modelo, ano) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.executeUpdate();
        }
    }

    // Método para atualizar veículo
    public void atualizarVeiculo(Veiculo veiculo) throws SQLException {
        String sql = "UPDATE veiculos SET placa = ?, modelo = ?, ano = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setInt(4, veiculo.getId());
            stmt.executeUpdate();
        }
    }

    // Método para remover veículo
    public void removerVeiculo(int id) throws SQLException {
        String sql = "DELETE FROM veiculos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Método para buscar um veículo por ID
    public Veiculo buscarVeiculoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM veiculos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Veiculo veiculo = new Veiculo();
                    veiculo.setId(rs.getInt("id"));
                    veiculo.setPlaca(rs.getString("placa"));
                    veiculo.setModelo(rs.getString("modelo"));
                    veiculo.setAno(rs.getInt("ano"));
                    return veiculo;
                }
            }
        }
        return null;
    }

    // Método para listar todos os veículos
    public List<Veiculo> listarTodosVeiculos() throws SQLException {
        String sql = "SELECT * FROM veiculos";
        List<Veiculo> veiculos = new ArrayList<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(rs.getInt("id"));
                veiculo.setPlaca(rs.getString("placa"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setAno(rs.getInt("ano"));
                veiculos.add(veiculo);
            }
        }
        return veiculos;
    }
}
