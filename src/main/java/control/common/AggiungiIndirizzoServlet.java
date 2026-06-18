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
import model.Indirizzo;
import model.dao.IndirizzoDAO;

@WebServlet("/common/aggiungiIndirizzo")
public class AggiungiIndirizzoServlet extends HttpServlet
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
        Utente utenteLoggato = (Utente) session.getAttribute("utente");
        
        String viaNumciv = request.getParameter("via_numciv");
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");
        String codicePostale = request.getParameter("codice_postale");
        String paese = request.getParameter("paese");
        
        String predefinitoStr = request.getParameter("predefinito");
        boolean predefinito = "true".equals(predefinitoStr);

        if (viaNumciv != null && !viaNumciv.trim().isEmpty() && 
            citta != null && !citta.trim().isEmpty() && 
            codicePostale != null && !codicePostale.trim().isEmpty())
        {
            try
            {
                Indirizzo nuovoIndirizzo = new Indirizzo();
                nuovoIndirizzo.setViaNumciv(viaNumciv.trim());
                nuovoIndirizzo.setCitta(citta.trim());
                nuovoIndirizzo.setProvincia(provincia != null ? provincia.trim() : "");
                nuovoIndirizzo.setCodicePostale(codicePostale.trim());
                nuovoIndirizzo.setPaese(paese != null ? paese.trim() : "Italia");
                nuovoIndirizzo.setPredefinito(predefinito);
                
                nuovoIndirizzo.setFkUtente(utenteLoggato.getIdUtente());
                indirizzoDAO.doSave(nuovoIndirizzo);
                
                session.setAttribute("messaggioSuccesso", "Nuovo indirizzo salvato con successo!");
                
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Errore SQL durante l'inserimento dell'indirizzo: " + e.getMessage());
                
                session.setAttribute("messaggioErrore", "Errore interno del database durante il salvataggio dell'indirizzo.");
            }
        } else {
            // Caso in cui i dati obbligatori arrivino vuoti alla servlet
            session.setAttribute("messaggioErrore", "I campi Via, Città e CAP sono obbligatori.");
        }
        
        response.sendRedirect(request.getContextPath() + "/common/profilo");
    }
}