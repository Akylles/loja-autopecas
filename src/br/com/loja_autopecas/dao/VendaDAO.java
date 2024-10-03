package br.com.loja_autopecas.dao;

import br.com.loja_autopecas.entidades.Venda;
import br.com.loja_autopecas.entidades.Cliente;
import br.com.loja_autopecas.entidades.Funcionario;
import br.com.loja_autopecas.entidades.Peca;
import br.com.loja_autopecas.entidades.Servico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    private Connection conn;

    public VendaDAO(Connection conn) {
        this.conn = conn;
    }

    // Método para adicionar venda
    public void adicionarVenda(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (cliente_id, funcionario_id, servico_id, valor_total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, venda.getCliente().getId());
            stmt.setInt(2, venda.getFuncionario().getId());
            stmt.setInt(3, venda.getServico().getId());
            stmt.setDouble(4, venda.getValorTotal());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    venda.setId(generatedKeys.getInt(1));
                }
            }

            // Adicionando as peças na tabela intermediária venda_peca
            for (Peca peca : venda.getPecas()) {
                adicionarPecaNaVenda(venda.getId(), peca.getId());
            }
        }
    }

    private void adicionarPecaNaVenda(int vendaId, int pecaId) throws SQLException {
        String sql = "INSERT INTO venda_peca (venda_id, peca_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vendaId);
            stmt.setInt(2, pecaId);
            stmt.executeUpdate();
        }
    }

    // Método para atualizar venda
    public void atualizarVenda(Venda venda) throws SQLException {
        String sql = "UPDATE vendas SET cliente_id = ?, funcionario_id = ?, servico_id = ?, valor_total = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, venda.getCliente().getId());
            stmt.setInt(2, venda.getFuncionario().getId());
            stmt.setInt(3, venda.getServico().getId());
            stmt.setDouble(4, venda.getValorTotal());
            stmt.setInt(5, venda.getId());
            stmt.executeUpdate();
        }
    }

    // Método para remover venda
    public void removerVenda(int id) throws SQLException {
        String sql = "DELETE FROM vendas WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Método para buscar venda por ID
    public Venda buscarVendaPorId(int id) throws SQLException {
        String sql = "SELECT * FROM vendas WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Venda venda = new Venda();
                    venda.setId(rs.getInt("id"));
                    venda.setCliente(new ClienteDAO(conn).buscarClientePorId(rs.getInt("cliente_id")));
                    venda.setFuncionario(new FuncionarioDAO(conn).buscarFuncionarioPorId(rs.getInt("funcionario_id")));
                    venda.setServico(new ServicoDAO(conn).buscarServicoPorId(rs.getInt("servico_id")));
                    venda.setValorTotal(rs.getDouble("valor_total"));
                    return venda;
                }
            }
        }
        return null;
    }

    // Método para listar todas as vendas
    public List<Venda> listarTodasVendas() throws SQLException {
        String sql = "SELECT * FROM vendas";
        List<Venda> vendas = new ArrayList<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setCliente(new ClienteDAO(conn).buscarClientePorId(rs.getInt("cliente_id")));
                venda.setFuncionario(new FuncionarioDAO(conn).buscarFuncionarioPorId(rs.getInt("funcionario_id")));
                venda.setServico(new ServicoDAO(conn).buscarServicoPorId(rs.getInt("servico_id")));
                venda.setValorTotal(rs.getDouble("valor_total"));
                vendas.add(venda);
            }
        }
        return vendas;
    }
}
