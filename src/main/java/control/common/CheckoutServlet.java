package control.common;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import model.Carrello;
import model.Indirizzo;
import model.MetodoPagamento;
import model.Ordine;
import model.dao.OrdineDAO;
import model.dao.IndirizzoDAO;   
import model.dao.MetodoPagamentoDAO;

@WebServlet("/common/checkout")
public class CheckoutServlet extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    
    private OrdineDAO ordineDAO;
    private IndirizzoDAO indirizzoDAO;
    private MetodoPagamentoDAO metodoPagamentoDAO;

    @Override
    public void init() throws ServletException 
    {
        this.ordineDAO = new OrdineDAO();
        this.indirizzoDAO = new IndirizzoDAO();
        this.metodoPagamentoDAO = new MetodoPagamentoDAO();
    }

    // 1. QUESTO METODO VISUALIZZA IL FORM DI CHECKOUT (Richiesta GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        Utente utenteLoggato = (Utente) session.getAttribute("utente");
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        //se il carrello è vuoto non ha senso fare il checkout
        if (carrello == null || carrello.getElementi().isEmpty()) 
        {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        try 
        {
            // Recuperiamo gli indirizzi e le carte dell'utente dal database
            List<Indirizzo> indirizzi = indirizzoDAO.doRetrieveByUtente(utenteLoggato.getIdUtente());
            List<MetodoPagamento> carte = metodoPagamentoDAO.doRetrieveByUtente(utenteLoggato.getIdUtente()); 

            request.setAttribute("indirizzi", indirizzi);
            request.setAttribute("carte", carte);
            request.getRequestDispatcher("/WEB-INF/view/common/checkout.jsp").forward(request, response);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento dei dati di checkout.");
        }
    }

    // 2. QUESTO METODO RICEVE I DATI DEL FORM E INVIA L'ORDINE (Richiesta POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        Utente utenteLoggato = (Utente) session.getAttribute("utente");
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        if (utenteLoggato == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (carrello == null || carrello.getElementi().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        String idIndirizzoStr = request.getParameter("idIndirizzoSelezionato");
        String idMetodoStr = request.getParameter("idMetodoSelezionato");

        // Controllo robusto: se manca la selezione, rimanda indietro con un messaggio d'errore
        if (idIndirizzoStr == null || idIndirizzoStr.trim().isEmpty() || idMetodoStr == null || idMetodoStr.trim().isEmpty()) {
            session.setAttribute("messaggioErrore", "Seleziona un indirizzo e un metodo di pagamento validi prima di acquistare.");
            response.sendRedirect(request.getContextPath() + "/common/checkout");
            return;
        }

        try 
        {
            int idIndirizzo = Integer.parseInt(idIndirizzoStr);
            int idMetodo = Integer.parseInt(idMetodoStr);

            Ordine ordine = new Ordine();
            ordine.setFkUtente(utenteLoggato.getIdUtente());
            ordine.setFkIndirizzo(idIndirizzo);
            ordine.setFkPagamento(idMetodo);
            ordine.setTotale(carrello.getTotaleComplessivo());
            
            // FORZATO IN MAIUSCOLO PER DIRETTIVA ENUM DB
            ordine.setStato("IN_PREPARAZIONE"); 
            ordine.setDataOrdine(Date.valueOf(LocalDate.now())); 

            int idOrdineGenerato = ordineDAO.salvaOrdineCompleto(ordine, carrello);

            if (idOrdineGenerato > 0) {
                session.removeAttribute("carrello");
                request.setAttribute("idOrdineSuccesso", idOrdineGenerato);
                request.getRequestDispatcher("/WEB-INF/view/common/ordineSuccesso.jsp").forward(request, response);
            } else {
                throw new SQLException("Errore: il database non ha restituito un ID valido per l'ordine.");
            }
        } 
        catch (NumberFormatException e) {
            System.err.println("ID corrotti nel Checkout: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/common/checkout");
        } 
        catch (SQLException e) {
            // Questo ti stampa l'errore preciso del database nella console del server!
            System.err.println("CRASH DB NEL CHECKOUT:");
            e.printStackTrace(); 
            
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno del database: " + e.getMessage());
        }
    }
}