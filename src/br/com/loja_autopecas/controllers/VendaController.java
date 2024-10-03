package br.com.loja_autopecas.controllers;

import br.com.loja_autopecas.dao.VendaDAO;
import br.com.loja_autopecas.entidades.Venda;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/vendas")
public class VendaController extends HttpServlet {

    private VendaDAO vendaDAO;

    @Override
    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("connection");
        vendaDAO = new VendaDAO(conn);
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
                    removerVenda(request, response);
                    break;
                default:
                    listarVendas(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void mostrarFormularioCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("venda-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Venda vendaExistente = vendaDAO.buscarVendaPorId(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("venda-form.jsp");
        request.setAttribute("venda", vendaExistente);
        dispatcher.forward(request, response);
    }

    private void listarVendas(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Venda> listaVendas = vendaDAO.listarTodasVendas();
        request.setAttribute("listaVendas", listaVendas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("venda-list.jsp");
        dispatcher.forward(request, response);
    }

    private void removerVenda(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        vendaDAO.removerVenda(id);
        response.sendRedirect("vendas");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String clienteId = request.getParameter("clienteId");
        String dataVenda = request.getParameter("dataVenda");
        String total = request.getParameter("total");

        Venda venda = new Venda();
        venda.setClienteId(Integer.parseInt(clienteId));
        venda.setDataVenda(dataVenda);
        venda.setTotal(Double.parseDouble(total));

        try {
            if (id == null || id.isEmpty()) {
                vendaDAO.adicionarVenda(venda);
            } else {
                venda.setId(Integer.parseInt(id));
                vendaDAO.atualizarVenda(venda);
            }
            response.sendRedirect("vendas");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
