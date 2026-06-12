package control.admin;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.OrdineDAO;

@WebServlet("/admin/ordineStato")
public class StatoRuoloServlet extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    private OrdineDAO ordineDAO;

    @Override
    public void init() throws ServletException
    {
        this.ordineDAO = new OrdineDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        String idParam = request.getParameter("id");
        String nuovoStato = request.getParameter("nuovoStato");

        // 1. Validazione dei parametri: controlliamo che non siano vuoti
        if (idParam == null || idParam.trim().isEmpty() || nuovoStato == null || nuovoStato.trim().isEmpty())
        {
            session.setAttribute("errorMessage", "Dati mancanti per l'aggiornamento dell'ordine.");
            response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=ordini");
            return;
        }

        try 
        {
            int idOrdine = Integer.parseInt(idParam);

            if (!"IN_PREPARAZIONE".equals(nuovoStato) && !"SPEDITO".equals(nuovoStato) && !"CONSEGNATO".equals(nuovoStato))
            {
                session.setAttribute("errorMessage", "Stato ordine non valido.");
                response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=ordini");
                return;
            }

            boolean aggiornato = ordineDAO.updateStato(idOrdine, nuovoStato);

            if (aggiornato)
            {
                session.setAttribute("successMessage", "Stato dell'ordine #" + idOrdine + " aggiornato in: " + nuovoStato);
            } else {
                session.setAttribute("errorMessage", "Impossibile aggiornare l'ordine. ID ordine non trovato.");
            }
        } 
        catch (NumberFormatException e) {
            System.err.println("[ERRORE] ID Ordine non numerico ricevuto: " + idParam);
            session.setAttribute("errorMessage", "Formato dell'ID ordine non valido.");
        } 
        catch (SQLException e) {
            System.err.println("[CRASH DB] Errore durante l'update dello stato ordine:");
            e.printStackTrace(); 
            session.setAttribute("errorMessage", "Errore interno del database durante il cambio di stato.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=ordini");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=ordini");
    }
}