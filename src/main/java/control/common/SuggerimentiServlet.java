package control.common;

import model.Prodotto;
import model.dao.ProdottoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/suggerimenti")
public class SuggerimentiServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO;

    @Override
    public void init() throws ServletException
    {
        this.prodottoDAO = new ProdottoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String query = request.getParameter("q");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (query == null || query.trim().length() < 2)
        {
            out.print("[]");
            return;
        }

        try
        {
            List<Prodotto> suggeriti = prodottoDAO.doRetrieveByNomeUnivoci(query.trim());
            
            //l'array JSON che conterrà i prodotti
            org.json.JSONArray jsonArray = new org.json.JSONArray();
            
            for (Prodotto p : suggeriti) 
            {
                //per ogni prodotto crea un oggetto JSON
                org.json.JSONObject jsonOggetto = new org.json.JSONObject();
                
                //inserisce i dati
                jsonOggetto.put("id", p.getIdProdotto());
                jsonOggetto.put("nome", p.getNome());
                jsonOggetto.put("squadra", p.getSquadra());
                
                //aggiunge il singolo prodotto all'array principale
                jsonArray.put(jsonOggetto);
            }
            
            // trasforma tutto in stringa e lo invia
            out.print(jsonArray.toString());
        } catch (Exception e) {
            log("Errore nella generazione dei suggerimenti", e);
            out.print("[]");
        }
    }
}