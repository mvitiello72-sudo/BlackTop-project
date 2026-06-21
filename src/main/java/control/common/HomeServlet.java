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
        this.p = new ProdottoDAO();
    }
      
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            //Prendiamo solo i prodotti univoci dal DB per evitare duplicati di taglia
            List<Prodotto> tuttiProdotti = p.doRetrieveAllAttiviUnivoci();            
            
            //prendiamo solo alcuni prodotti per metterli in evidenza
            List<Prodotto> prodottiEvidenza = tuttiProdotti.stream()
                    .limit(8)
                    .collect(Collectors.toList());
            
            // Passiamo la lista alla jsp
            request.setAttribute("prodottiEvidenza", prodottiEvidenza);
        }
        catch(Exception e)
        {
            System.out.println("DEBUG - ERRORE NEL COSTRUTTORE O NEL DAO:");
            e.printStackTrace();
        }
        
        // Inoltra la richiesta alla pagina JSP 
        request.getRequestDispatcher("/WEB-INF/view/common/home.jsp").forward(request, response);        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}