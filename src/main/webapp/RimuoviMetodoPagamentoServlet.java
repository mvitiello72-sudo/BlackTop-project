package control.common;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import model.dao.MetodoPagamentoDAO;

@WebServlet("/rimuoviPagamento")
public class RimuoviMetodoPagamentoServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private MetodoPagamentoDAO pagamentoDAO;

    @Override
    public void init() throws ServletException
    {
        this.pagamentoDAO = new MetodoPagamentoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.sendRedirect(request.getContextPath() + "/profilo");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Utente utenteLoggato = (Utente) session.getAttribute("utente");

        if (utenteLoggato == null)
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idPagamentoStr = request.getParameter("idMetodo");

        if (idPagamentoStr != null && !idPagamentoStr.trim().isEmpty())
        {
            try
            {
                int idPagamentoInt = Integer.parseInt(idPagamentoStr);
                pagamentoDAO.doDelete(idPagamentoInt);
                
                session.setAttribute("messaggioSuccesso", "Metodo di pagamento rimosso con successo.");

            } catch (NumberFormatException e) {
                System.err.println("ID Pagamento non valido: " + idPagamentoStr);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Errore SQL durante la rimozione del pagamento: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile rimuovere il metodo di pagamento.");
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/profilo");
    }
}