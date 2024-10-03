package br.com.loja_autopecas.controllers;

import br.com.loja_autopecas.dao.FuncionarioDAO;
import br.com.loja_autopecas.entidades.Funcionario;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/funcionarios")
public class FuncionarioController extends HttpServlet {

    private FuncionarioDAO funcionarioDAO;

    @Override
    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("connection");
        funcionarioDAO = new FuncionarioDAO(conn);
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
                    removerFuncionario(request, response);
                    break;
                default:
                    listarFuncionarios(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void mostrarFormularioCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("funcionario-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Funcionario funcionarioExistente = funcionarioDAO.buscarFuncionarioPorId(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("funcionario-form.jsp");
        request.setAttribute("funcionario", funcionarioExistente);
        dispatcher.forward(request, response);
    }

    private void listarFuncionarios(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Funcionario> listaFuncionarios = funcionarioDAO.listarTodosFuncionarios();
        request.setAttribute("listaFuncionarios", listaFuncionarios);
        RequestDispatcher dispatcher = request.getRequestDispatcher("funcionario-list.jsp");
        dispatcher.forward(request, response);
    }

    private void removerFuncionario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        funcionarioDAO.removerFuncionario(id);
        response.sendRedirect("funcionarios");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String cargo = request.getParameter("cargo");

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCargo(cargo);

        try {
            if (id == null || id.isEmpty()) {
                funcionarioDAO.adicionarFuncionario(funcionario);
            } else {
                funcionario.setId(Integer.parseInt(id));
                funcionarioDAO.atualizarFuncionario(funcionario);
            }
            response.sendRedirect("funcionarios");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
