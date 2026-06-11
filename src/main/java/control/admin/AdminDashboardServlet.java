package control.admin;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Prodotto;
import model.Utente;
import model.Ordine;
import model.dao.ProdottoDAO;
import model.dao.UtenteDAO;
import model.dao.OrdineDAO; 

@WebServlet("/admin/admindashboard")
public class AdminDashboardServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private ProdottoDAO prodottoDAO = new ProdottoDAO();
    private UtenteDAO utenteDAO = new UtenteDAO();
    private OrdineDAO ordineDAO = new OrdineDAO(); 

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
        // GESTIONE MESSAGGI DI FEEDBACK
        String successMessage = (String) session.getAttribute("successMessage");
        String errorMessage = (String) session.getAttribute("errorMessage");
        
        if (successMessage != null)
        {
            request.setAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        if (errorMessage != null)
        {
            request.setAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        try
        {
            int totaleProdotti = prodottoDAO.countProdottiAttivi(); 
            int totaleUtenti = utenteDAO.countUtentiRegistrati();
            int totaleOrdini = ordineDAO.countOrdiniTotali();

            List<Prodotto> listaProdotti = prodottoDAO.doRetrieveAll(); 
            List<Utente> listaUtenti = utenteDAO.doRetrieveAll();

            String dataInizio = request.getParameter("dataInizio");
            String dataFine = request.getParameter("dataFine");
            String cliente = request.getParameter("cliente");

            List<Ordine> listaOrdini;

            // Se almeno uno dei filtri è applicato, usiamo il recupero con filtri
            if ((dataInizio != null && !dataInizio.isEmpty()) || 
                (dataFine != null && !dataFine.isEmpty()) || 
                (cliente != null && !cliente.isEmpty())) 
            {
                listaOrdini = ordineDAO.doRetrieveByFilters(dataInizio, dataFine, cliente);
            } 
            else 
            {
                // Altrimenti carichiamo tutti gli ordini normalmente
                listaOrdini = ordineDAO.doRetrieveAllConUtenti();
            }

            // 4. SETTAGGIO ATTRIBUTI PER LA JSP
            request.setAttribute("totaleProdotti", totaleProdotti);
            request.setAttribute("totaleUtenti", totaleUtenti);
            request.setAttribute("totaleOrdini", totaleOrdini);
            
            request.setAttribute("prodotti", listaProdotti);
            request.setAttribute("utenti", listaUtenti);
            request.setAttribute("ordini", listaOrdini);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore nel caricamento dei dati dal database: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/view/admin/adminDashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}