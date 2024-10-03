package br.com.loja_autopecas.controllers;

import br.com.loja_autopecas.dao.ClienteDAO;
import br.com.loja_autopecas.entidades.Cliente;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/clientes")
public class ClienteController extends HttpServlet {

    private ClienteDAO clienteDAO;

    @Override
    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("connection");
        clienteDAO = new ClienteDAO(conn);
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
                    removerCliente(request, response);
                    break;
                default:
                    listarClientes(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void mostrarFormularioCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("cliente-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Cliente clienteExistente = clienteDAO.buscarClientePorId(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cliente-form.jsp");
        request.setAttribute("cliente", clienteExistente);
        dispatcher.forward(request, response);
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Cliente> listaClientes = clienteDAO.listarTodosClientes();
        request.setAttribute("listaClientes", listaClientes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cliente-list.jsp");
        dispatcher.forward(request, response);
    }

    private void removerCliente(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        clienteDAO.removerCliente(id);
        response.sendRedirect("clientes");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);

        try {
            if (id == null || id.isEmpty()) {
                clienteDAO.adicionarCliente(cliente);
            } else {
                cliente.setId(Integer.parseInt(id));
                clienteDAO.atualizarCliente(cliente);
            }
            response.sendRedirect("clientes");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
