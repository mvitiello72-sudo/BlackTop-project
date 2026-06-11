package control.common;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        if (carrello == null) 
        {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        String action = request.getParameter("action");

        if (action != null) 
        {
            try 
            {
                if (action.equals("add")) 
                {
                    int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
                    int quantita = Integer.parseInt(request.getParameter("quantita"));
                    // Recupera la taglia selezionata dall'utente nel form del prodotto
                    String taglia = request.getParameter("taglia"); 

                    Prodotto p = prodottoDAO.doRetrieveByKey(idProdotto);
                    
                    if (p != null) 
                    {        
                        // Se la taglia non è passata dal form, usa quella di default del DB
                        if (taglia == null || taglia.trim().isEmpty()) {
                            taglia = p.getTaglia();
                        }
                        
                        if (taglia != null && !taglia.trim().isEmpty()) 
                        {
                            carrello.aggiungiProdotto(p, taglia, quantita);
                        }
                    }
                } 
                else if (action.equals("remove")) 
                {
                    int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
                    String taglia = request.getParameter("taglia");

                    carrello.rimuoviProdotto(idProdotto, taglia);
                }
                else if (action.equals("update"))
                {
                    int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
                    String taglia = request.getParameter("taglia");
                    int nuovaQuantita = Integer.parseInt(request.getParameter("quantita"));

                    Prodotto p = prodottoDAO.doRetrieveByKey(idProdotto);
                    if (p != null) 
                    {
                        if (nuovaQuantita > p.getStock()) 
                        {
                            nuovaQuantita = p.getStock(); // Protezione stock massimo
                        }
                        
                        if (nuovaQuantita > 0) 
                        {
                            // Invia l'oggetto Prodotto 'p' intero per aggiornare i dati in tempo reale
                            carrello.aggiornaQuantita(p, taglia, nuovaQuantita); 
                        }
                    }
                }
            } 
            catch (Exception e) 
            {
                System.err.println("Errore nell'elaborazione del carrello: " + e.getMessage());
                e.printStackTrace(); // Ti permette di vedere l'errore esatto nella console di Tomcat
            }
        }

        response.sendRedirect(request.getContextPath() + "/carrello");
    }
}