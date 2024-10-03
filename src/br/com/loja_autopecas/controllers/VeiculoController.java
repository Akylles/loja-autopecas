package br.com.loja_autopecas.controllers;

import br.com.loja_autopecas.dao.VeiculoDAO;
import br.com.loja_autopecas.entidades.Veiculo;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/veiculos")
public class VeiculoController extends HttpServlet {

    private VeiculoDAO veiculoDAO;

    @Override
    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("connection");
        veiculoDAO = new VeiculoDAO(conn);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action != null ? action : "") {
                case "new":
                    mostrarFormularioCadastro(request, response);
                    break;
                case "edit":
                    mostrarFormularioEdicao(request, response);
                    break;
                case "delete":
                    removerVeiculo(request, response);
                    break;
                default:
                    listarVeiculos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void mostrarFormularioCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("veiculo-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Veiculo veiculoExistente = veiculoDAO.buscarVeiculoPorId(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("veiculo-form.jsp
                request.setAttribute("veiculo", veiculoExistente);
        dispatcher.forward(request, response);
    }

    private void listarVeiculos(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Veiculo> listaVeiculos = veiculoDAO.listarTodosVeiculos();
        request.setAttribute("listaVeiculos", listaVeiculos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("veiculo-list.jsp");
        dispatcher.forward(request, response);
    }

    private void removerVeiculo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        veiculoDAO.removerVeiculo(id);
        response.sendRedirect("veiculos");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String modelo = request.getParameter("modelo");
        String marca = request.getParameter("marca");
        String ano = request.getParameter("ano");
        String placa = request.getParameter("placa");

        Veiculo veiculo = new Veiculo();
        veiculo.setModelo(modelo);
        veiculo.setMarca(marca);
        veiculo.setAno(Integer.parseInt(ano));
        veiculo.setPlaca(placa);

        try {
            if (id == null || id.isEmpty()) {
                veiculoDAO.adicionarVeiculo(veiculo);
            } else {
                veiculo.setId(Integer.parseInt(id));
                veiculoDAO.atualizarVeiculo(veiculo);
            }
            response.sendRedirect("veiculos");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
