package control.common;

import model.Prodotto;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/prodotto")
public class ProdottoServlet extends HttpServlet
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
	    String idString = request.getParameter("id");
	    
	    Prodotto p = null;
	    List<Prodotto> prodottiCorrelati = null;

	    if (idString != null && !idString.trim().isEmpty()) 
	    {
	        try 
	        {
	            int idProdotto = Integer.parseInt(idString); 
	            p = prodottoDAO.doRetrieveByKey(idProdotto);
	            
	            // Se il prodotto principale esiste, cerchiamo i correlati
	            if (p != null)
	            {
	                prodottiCorrelati = prodottoDAO.doRetrieveCorrelati(p.getSquadra(), p.getIdProdotto());
	            }
	        } 
	        catch (NumberFormatException e) 
	        {
	            System.err.println("ID prodotto non valido: " + idString);
	        }
	        catch (Exception e) 
	        {
	            // Cattura sia gli errori di doRetrieveByKey che di doRetrieveCorrelati
	            System.err.println("Errore nel recupero dei dati dal DB: " + e.getMessage());
	        }
	    }
	    
	    request.setAttribute("prodotto", p);
	    request.setAttribute("prodottiCorrelati", prodottiCorrelati);
	    
	    request.getRequestDispatcher("/WEB-INF/view/common/prodotto.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}