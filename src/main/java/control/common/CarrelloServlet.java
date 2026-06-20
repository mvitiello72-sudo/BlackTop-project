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
        
        // Questa variabile ci serve per capire a quale pagina reindirizzare alla fine del metodo
        boolean isAggiuntaProdotto = false;
        int idProdottoDestinazione = -1;

        if (action != null) 
        {
            try 
            {
            		if (action.equals("add")) //aggiuge al carrello
            		{
            			int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
            			int quantita = Integer.parseInt(request.getParameter("quantita"));
            	    
            			// Recuperiamo il prodotto
            			Prodotto p = prodottoDAO.doRetrieveByKey(idProdotto);
            	    
            			if (p != null) 
            			{   
            				String tagliaReale = p.getTaglia();
            	        
            				if (tagliaReale != null && !tagliaReale.trim().isEmpty()) 
            				{
            					carrello.aggiungiProdotto(p, tagliaReale, quantita);
            	        
            					session.setAttribute("prodottoAggiunto", true);
            	            
            					isAggiuntaProdotto = true;
            					idProdottoDestinazione = idProdotto;
            				}
            			}
            		}
                else if (action.equals("remove")) //rimuove dal carrello
                {
                    int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
                    String taglia = request.getParameter("taglia");

                    carrello.rimuoviProdotto(idProdotto, taglia);
                }
                else if (action.equals("update")) //modifica la quantità di un prodotto nel carrello
                {
                    int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
                    String taglia = request.getParameter("taglia");
                    int nuovaQuantita = Integer.parseInt(request.getParameter("quantita"));

                    Prodotto p = prodottoDAO.doRetrieveByKey(idProdotto);
                    if (p != null) 
                    {
                        if (nuovaQuantita > p.getStock()) 
                        {
                            nuovaQuantita = p.getStock(); 
                        }
                        
                        if (nuovaQuantita > 0) 
                        {
                            carrello.aggiornaQuantita(p, taglia, nuovaQuantita); 
                        }
                    }
                }
            } 
            catch (Exception e) 
            {
                System.err.println("Errore nell'elaborazione del carrello: " + e.getMessage());
                e.printStackTrace(); 
            }
        }

        // Se abbiamo appena aggiunto un prodotto, rimaniamo sulla pagina del dettaglio prodotto
        if (isAggiuntaProdotto && idProdottoDestinazione != -1) 
        {
            response.sendRedirect(request.getContextPath() + "/prodotto?id=" + idProdottoDestinazione);
        } 
        else 
        {
            // Per le altre azioni (rimozione o aggiornamento dentro la pagina carrello), andiamo al carrello come prima
            response.sendRedirect(request.getContextPath() + "/carrello");
        }
    }
}