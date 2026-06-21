package control.common;

import model.Utente;
import model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException 
    {
        this.utenteDAO = new UtenteDAO();
    }

    //mostra il form di registrazione
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Se l'utente è già loggato, lo rimandiamo alla home 
        HttpSession session = request.getSession();
        if (session.getAttribute("utente") != null) 
        {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
     
        request.getRequestDispatcher("/WEB-INF/view/common/registrazione.jsp").forward(request, response);
    }

    //l'utente invia i dati per reggistrarsi
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String cellulare = request.getParameter("cellulare");

        // Controllo di validazione base lato server
        if (nome == null || cognome == null || email == null || password == null ||
            nome.trim().isEmpty() || cognome.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) 
        {
            request.setAttribute("errore", "Tutti i campi contrassegnati con l'asterisco sono obbligatori.");
            request.getRequestDispatcher("/WEB-INF/view/common/registrazione.jsp").forward(request, response);
            return;
        }
        
        try 
        {
            //Verifichiamo se esiste già un utente con questa email
            Utente utenteEsistente = utenteDAO.doRetrieveByEmail(email);
            
            if (utenteEsistente != null) 
            {
                // Se l'email è già registrata, rimandiamo al form mostrando l'errore
                request.setAttribute("errore", "Questo indirizzo email è già associato a un account BlackTop.");
                request.getRequestDispatcher("/WEB-INF/view/common/registrazione.jsp").forward(request, response);
                return;
            }

            Utente nuovoUtente = new Utente();
            nuovoUtente.setNome(nome);
            nuovoUtente.setCognome(cognome);
            nuovoUtente.setEmail(email);
            
            //passiamo la pass in chiaro, il metodo doSave farà la cifratura
            nuovoUtente.setPassword(password); 
            
            nuovoUtente.setRuolo("USER");
            nuovoUtente.setCellulare(cellulare);
            
            utenteDAO.doSave(nuovoUtente);

            //Recuperiamo l'utente appena salvato 
            Utente utenteLoggato = utenteDAO.doRetrieveByEmail(email);

            //creiamo la sessione
            HttpSession session = request.getSession();
            session.setAttribute("utente", utenteLoggato);

            response.sendRedirect(request.getContextPath() + "/home");
        } 
        catch (SQLException e) 
        {
            System.err.println("Errore SQL durante la registrazione utente: " + e.getMessage());
            request.setAttribute("errore", "Si è verificato un errore tecnico. Riprova più tardi.");
            request.getRequestDispatcher("/WEB-INF/view/common/registrazione.jsp").forward(request, response);
        }
    }
}