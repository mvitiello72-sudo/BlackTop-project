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

@WebServlet("/impostaIndirizzoPredefinito")
public class ImpostaIndirizzoPredefinitoServlet extends HttpServlet
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

        String idIndirizzoStr = request.getParameter("idIndirizzo");

        if (idIndirizzoStr != null && !idIndirizzoStr.trim().isEmpty())
        {
            try {
                int idIndirizzo = Integer.parseInt(idIndirizzoStr);
                int idUtente = utenteLoggato.getIdUtente();

                indirizzoDAO.cambiaStatoPredefinito(idUtente, idIndirizzo);
                
                session.setAttribute("messaggioSuccesso", "Indirizzo predefinito aggiornato!");

            } catch (NumberFormatException e) {
                System.err.println("ID Indirizzo non valido: " + idIndirizzoStr);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Errore SQL durante il cambio dell'indirizzo predefinito: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile aggiornare l'indirizzo predefinito.");
                return;
            }
        }

        // 4. Rimbalzo sulla ProfiloServlet per rinfrescare la pagina con le modifiche visibili
        response.sendRedirect(request.getContextPath() + "/profilo");
    }
}