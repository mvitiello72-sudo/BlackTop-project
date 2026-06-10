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
import model.dao.UtenteDAO;

@WebServlet("/aggiornaProfilo")
public class AggiornaProfiloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException
    {
        this.utenteDAO = new UtenteDAO();
    }

    // Blocchiamo l'accesso diretto in GET (es. se qualcuno scrive l'URL nel browser)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.sendRedirect(request.getContextPath() + "/profilo");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Utente utenteLoggato = (Utente) session.getAttribute("utente");

        //se l'utente non è loggato
        if (utenteLoggato == null)
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String cellulare = request.getParameter("cellulare");
        String nuovaPassword = request.getParameter("password");

        // Validazione base dei campi obbligatori
        if (nome == null || nome.trim().isEmpty() || cognome == null || cognome.trim().isEmpty())
        {
            request.setAttribute("erroreProfilo", "Nome e Cognome sono campi obbligatori.");
            request.getRequestDispatcher("/WEB-INF/view/common/profilo.jsp").forward(request, response);
            return;
        }

        try
        {
            utenteLoggato.setNome(nome.trim());
            utenteLoggato.setCognome(cognome.trim());
            utenteLoggato.setCellulare(cellulare != null ? cellulare.trim() : null);

            // Gestione Password: la aggiorniamo solo se l'utente ha scritto qualcosa nel campo
            if (nuovaPassword != null && !nuovaPassword.trim().isEmpty())
            {
                utenteLoggato.setPassword(nuovaPassword.trim());
            }

            utenteDAO.doUpdate(utenteLoggato);

            session.setAttribute("utente", utenteLoggato);
            
            session.setAttribute("messaggioSuccesso", "Profilo aggiornato con successo!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Errore SQL durante l'aggiornamento del profilo: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno durante il salvataggio dei dati.");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/profilo");
    }
}