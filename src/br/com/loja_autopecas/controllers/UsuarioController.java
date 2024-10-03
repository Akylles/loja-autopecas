package br.com.loja_autopecas.controllers;

import br.com.loja_autopecas.dao.UsuarioDAO;
import br.com.loja_autopecas.entidades.Usuario;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioController extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("connection");
        usuarioDAO = new UsuarioDAO(conn);
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
                    removerUsuario(request, response);
                    break;
                default:
                    listarUsuarios(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void mostrarFormularioCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("usuario-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Usuario usuarioExistente = usuarioDAO.buscarUsuarioPorId(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("usuario-form.jsp");
        request.setAttribute("usuario", usuarioExistente);
        dispatcher.forward(request, response);
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Usuario> listaUsuarios = usuarioDAO.listarTodosUsuarios();
        request.setAttribute("listaUsuarios", listaUsuarios);
        RequestDispatcher dispatcher = request.getRequestDispatcher("usuario-list.jsp");
        dispatcher.forward(request, response);
    }

    private void removerUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        usuarioDAO.removerUsuario(id);
        response.sendRedirect("usuarios");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setSenha(senha);

        try {
            if (id == null || id.isEmpty()) {
                usuarioDAO.adicionarUsuario(usuario);
            } else {
                usuario.setId(Integer.parseInt(id));
                usuarioDAO.atualizarUsuario(usuario);
            }
            response.sendRedirect("usuarios");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
