package control.admin;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UtenteDAO; // Assicurati che il percorso del tuo DAO sia corretto

@WebServlet("/admin/utenteRuolo")
public class UtenteRuoloServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException {
        this.utenteDAO = new UtenteDAO(); // Inizializziamo il DAO per comunicare col DB
    }

    // Le modifiche di stato si fanno SOLO in POST per sicurezza
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String idParam = request.getParameter("id");
        String action = request.getParameter("action");

        // Verifichiamo che i parametri essenziali non siano vuoti
        if (idParam == null || idParam.trim().isEmpty() || action == null || action.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Parametri mancanti per la modifica del ruolo.");
            response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=utenti");
            return;
        }

        try
        {
            int idUtente = Integer.parseInt(idParam);
            String nuovoRuolo = null;

            // Decidiamo il nuovo ruolo in base all'azione inviata dalla JSP
            if ("promuovi".equals(action)) {
                nuovoRuolo = "ADMIN";
            } else if ("declassa".equals(action)) {
                nuovoRuolo = "USER";
            } else {
                session.setAttribute("errorMessage", "Azione non riconosciuta.");
                response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=utenti");
                return;
            }
            
            boolean aggiornato = utenteDAO.updateRuolo(idUtente, nuovoRuolo);

            if (aggiornato) {
                session.setAttribute("successMessage", "Ruolo dell'utente aggiornato con successo in " + nuovoRuolo + ".");
            } else {
                session.setAttribute("errorMessage", "Impossibile aggiornare l'utente. Verifica che esista nel database.");
            }

        }
        catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "ID utente non valido.");
        }
        catch (SQLException e) {
            e.printStackTrace(); // Stampa l'errore SQL nella console di Tomcat
            session.setAttribute("errorMessage", "Errore del database durante l'aggiornamento del ruolo.");
        }

        // Ritorniamo alla dashboard posizionandoci direttamente sul tab degli utenti
        response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=utenti");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Se qualcuno prova a chiamare la servlet in GET, lo rimandiamo alla dashboard senza fare danni
        response.sendRedirect(request.getContextPath() + "/admin/admindashboard");
    }
}