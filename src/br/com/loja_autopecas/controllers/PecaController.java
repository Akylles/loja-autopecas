package br.com.loja_autopecas.controllers;

import br.com.loja_autopecas.dao.PecaDAO;
import br.com.loja_autopecas.entidades.Peca;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/pecas")
public class PecaController extends HttpServlet {

    private PecaDAO pecaDAO;

    @Override
    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("connection");
        pecaDAO = new PecaDAO(conn);
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
                    removerPeca(request, response);
                    break;
                default:
                    listarPecas(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void mostrarFormularioCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("peca-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Peca pecaExistente = pecaDAO.buscarPecaPorId(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("peca-form.jsp");
        request.setAttribute("peca", pecaExistente);
        dispatcher.forward(request, response);
    }

    private void listarPecas(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Peca> listaPecas = pecaDAO.listarTodasPecas();
        request.setAttribute("listaPecas", listaPecas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("peca-list.jsp");
        dispatcher.forward(request, response);
    }

    private void removerPeca(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        pecaDAO.removerPeca(id);
        response.sendRedirect("pecas");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String preco = request.getParameter("preco");

        Peca peca = new Peca();
        peca.setNome(nome);
        peca.setDescricao(descricao);
        peca.setPreco(Double.parseDouble(preco));

        try {
            if (id == null || id.isEmpty()) {
                pecaDAO.adicionarPeca(peca);
            } else {
                peca.setId(Integer.parseInt(id));
                pecaDAO.atualizarPeca(peca);
            }
            response.sendRedirect("pecas");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
