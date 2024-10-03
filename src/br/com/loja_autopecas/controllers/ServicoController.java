package br.com.loja_autopecas.controllers;

import br.com.loja_autopecas.dao.ServicoDAO;
import br.com.loja_autopecas.entidades.Servico;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/servicos")
public class ServicoController extends HttpServlet {

    private ServicoDAO servicoDAO;

    @Override
    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("connection");
        servicoDAO = new ServicoDAO(conn);
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
                    removerServico(request, response);
                    break;
                default:
                    listarServicos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void mostrarFormularioCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("servico-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Servico servicoExistente = servicoDAO.buscarServicoPorId(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("servico-form.jsp");
        request.setAttribute("servico", servicoExistente);
        dispatcher.forward(request, response);
    }

    private void listarServicos(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Servico> listaServicos = servicoDAO.listarTodosServicos();
        request.setAttribute("listaServicos", listaServicos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("servico-list.jsp");
        dispatcher.forward(request, response);
    }

    private void removerServico(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        servicoDAO.removerServico(id);
        response.sendRedirect("servicos");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String descricao = request.getParameter("descricao");
        String valor = request.getParameter("valor");

        Servico servico = new Servico();
        servico.setDescricao(descricao);
        servico.setValor(Double.parseDouble(valor));

        try {
            if (id == null || id.isEmpty()) {
                servicoDAO.adicionarServico(servico);
            } else {
                servico.setId(Integer.parseInt(id));
                servicoDAO.atualizarServico(servico);
            }
            response.sendRedirect("servicos");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
