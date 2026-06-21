package control.common;

import model.Prodotto;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO;
    
    @Override
    public void init() throws ServletException
    {    
        this.prodottoDAO = new ProdottoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException
    {
        List<Prodotto> listaProdotti;
        
        // Parametro della barra di ricerca
        String queryCerca = request.getParameter("cerca");
        
        String[] squadre = request.getParameterValues("squadra");
        String categoria = request.getParameter("categoria");   
               
        try
        {
            // Se l'utente ha cercato qualcosa nella barra di ricerca
            if (queryCerca != null && !queryCerca.trim().isEmpty()) 
            {
                listaProdotti = prodottoDAO.doRetrieveByNomeUnivoci(queryCerca.trim());
            }
            else 
            {
                // Gestione dei filtri
                boolean nessunaSquadra = (squadre == null || squadre.length == 0);
                boolean nessunaCategoria = (categoria == null || categoria.equals("tutte"));
                
                if(nessunaSquadra && nessunaCategoria)
                {
                    // Prende solo i prodotti UNIVOCI per evitare i doppioni delle taglie nel catalogo
                    listaProdotti = prodottoDAO.doRetrieveAllAttiviUnivoci();                        
                }
                else
                {
                    listaProdotti = prodottoDAO.doRetrieveByFilter(squadre, categoria);
                }
            }
            
            request.setAttribute("listaProdotti", listaProdotti);
            
            request.getRequestDispatcher("/WEB-INF/view/common/catalogo.jsp").forward(request, response);
            
        } catch (Exception e) {
            log("Errore nel recupero dei prodotti per il catalogo", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                               "Si è verificato un errore nel caricamento del catalogo.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException
    {      
        doGet(request, response);
    }
}