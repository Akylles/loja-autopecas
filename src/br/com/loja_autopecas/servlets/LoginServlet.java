package br.com.loja_autopecas.servlets;

import br.com.loja_autopecas.dao.UsuarioDAO;
import br.com.loja_autopecas.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("postgres");
        String senha = request.getParameter("postgres");

        try (Connection conn = DBConnection.getConnection()) {
            UsuarioDAO dao = new UsuarioDAO(conn);
            if (dao.validarUsuario(login, senha)) {
                response.sendRedirect("jsp/index.jsp");
            } else {
                response.sendRedirect("jsp/login.jsp?erro=1");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
