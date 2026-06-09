package control.common;

import model.Prodotto;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = {"/HomeServlet", "/home", ""})
public class HomeServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ProdottoDAO p;
	
	@Override
    public void init() throws ServletException
	{
        // Inizializziamo il DAO una sola volta all'avvio della Servlet
        this.p = new ProdottoDAO();
    }
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			List<Prodotto> tuttiProdotti = p.doRetrieveAllAttivi();			
			
			//Ora prendiamo solo acluni prodotti per metterli in evidenzia
			List<Prodotto> prodottiEvidenza = tuttiProdotti.stream()
	                .filter(p -> p.getAttivo())
	                .limit(10)
	                .collect(Collectors.toList());
			
			//Passiamo la lista alla jsp
			request.setAttribute("prodottiEvidenza", prodottiEvidenza);
		}
		catch(Exception e)
		{
			System.out.println("DEBUG - ERRORE NEL COSTRUTTORE O NEL DAO:");
			e.printStackTrace();
		}
		
		//inoltra la richiesta alla pagina JSP 
        request.getRequestDispatcher("/WEB-INF/view/common/home.jsp").forward(request, response);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
