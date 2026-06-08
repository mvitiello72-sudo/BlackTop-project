package control;

import model.Carrello;
import model.Prodotto;
import model.dao.ProdottoDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/carrello")
public class CarrelloServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO;

    @Override
    public void init() throws ServletException 
    {
        this.prodottoDAO = new ProdottoDAO();
    }
      
    // 1. SCENARIO GET: L'utente vuole solo vedere la pagina del carrello
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        if (carrello == null) 
        {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }
        request.getRequestDispatcher("/WEB-INF/view/common/carrello.jsp").forward(request, response);
    }

 // 2. SCENARIO POST: Gestione Azioni (Aggiungi, Rimuovi, Modifica Quantità)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        if (carrello == null) 
        {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        // Vediamo quale form ha scatenato la richiesta ("add", "remove" o "update")
        String action = request.getParameter("action");

        if (action != null) 
        {
            try 
            {
                if (action.equals("add")) 
                {
                    int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
                    String taglia = request.getParameter("taglia");
                    int quantita = Integer.parseInt(request.getParameter("quantita"));

                    Prodotto p = prodottoDAO.doRetrieveByKey(idProdotto);
                    
                    if (p != null && taglia != null && !taglia.trim().isEmpty()) 
                    {
                        carrello.aggiungiProdotto(p, taglia, quantita);
                    }
                } 
                else if (action.equals("remove")) 
                {
                    int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
                    String taglia = request.getParameter("taglia");

                    // Rimuove l'accoppiata esatta prodotto-taglia
                    carrello.rimuoviProdotto(idProdotto, taglia);
                }
                else if (action.equals("update"))
                {
                    int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
                    String taglia = request.getParameter("taglia");
                    int nuovaQuantita = Integer.parseInt(request.getParameter("quantita"));

                    carrello.aggiornaQuantita(idProdotto, taglia, nuovaQuantita);
                }
            } 
            catch (Exception e) 
            {
                System.err.println("Errore nell'elaborazione del carrello: " + e.getMessage());
            }
        }

        // Reindirizziamo l'utente alla servlet stessa ma in GET 
        response.sendRedirect(request.getContextPath() + "/carrello");
    }
}