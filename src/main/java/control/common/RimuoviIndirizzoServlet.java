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
import model.dao.IndirizzoDAO;

@WebServlet("/common/rimuoviIndirizzo")
public class RimuoviIndirizzoServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private IndirizzoDAO indirizzoDAO;

    @Override
    public void init() throws ServletException
    {
        this.indirizzoDAO = new IndirizzoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.sendRedirect(request.getContextPath() + "/common/profilo");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        String idIndirizzoStr = request.getParameter("idIndirizzo");

        if (idIndirizzoStr != null && !idIndirizzoStr.trim().isEmpty())
        {
            try
            {
                int idIndirizzo = Integer.parseInt(idIndirizzoStr);

                indirizzoDAO.doDelete(idIndirizzo);
                session.setAttribute("messaggioSuccesso", "Indirizzo rimosso con successo.");

            } catch (NumberFormatException e) {
                System.err.println("ID Indirizzo non valido: " + idIndirizzoStr);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Errore SQL durante la rimozione dell'indirizzo: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile rimuovere l'indirizzo.");
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/common/profilo");
    }
}