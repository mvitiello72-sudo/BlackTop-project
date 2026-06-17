package control.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Prodotto;
import model.dao.ProdottoDAO;

@WebServlet("/prodotto")
public class ProdottoServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private final ProdottoDAO prodottoDAO = new ProdottoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String idParam = request.getParameter("id");
        
        // Controllo se il parametro ID è presente e valido
        if (idParam == null || idParam.trim().isEmpty())
        {
            response.sendRedirect(request.getContextPath() + "/catalogo");
            return;
        }

        try
        {
            int idProdotto = Integer.parseInt(idParam);
            
            Prodotto prodotto = prodottoDAO.doRetrieveByKey(idProdotto);
            
            if (prodotto != null && prodotto.getAttivo())
            {
                List<Prodotto> tuttiIProdotti = prodottoDAO.doRetrieveAllAttivi();
                
                // Filtra per trovare tutte le varianti di taglia disponibili per QUESTO modello
                List<Prodotto> variantiTaglia = new ArrayList<>();
                for (Prodotto p : tuttiIProdotti)
                {
                    // Se ha lo stesso nome ed è disponibile in magazzino, 
                    // consideriamo che è lo stesso articolo, con una taglia diversa
                    if (p.getNome().equalsIgnoreCase(prodotto.getNome()) && p.getStock() > 0)
                    {
                        variantiTaglia.add(p);
                    }
                }
                
                // MODIFICATO: Ora passiamo il NOME del prodotto per escludere i correlati duplicati
                List<Prodotto> prodottiCorrelati = prodottoDAO.doRetrieveCorrelati(prodotto.getSquadra(), prodotto.getNome());
               
                request.setAttribute("prodotto", prodotto);
                request.setAttribute("variantiTaglia", variantiTaglia);
                request.setAttribute("prodottiCorrelati", prodottiCorrelati);
                
                request.getRequestDispatcher("/WEB-INF/view/common/prodotto.jsp").forward(request, response);
                
            }
            else
            {
                // Se il prodotto non esiste o è stato disattivato dall'admin
                request.setAttribute("prodotto", null);
                request.getRequestDispatcher("/WEB-INF/view/common/prodotto.jsp").forward(request, response);
            }
            
        } catch (NumberFormatException e) {
            // Se l'ID passato nell'URL non è un numero valido (es. /prodotto?id=abc)
            response.sendRedirect(request.getContextPath() + "/catalogo");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante il recupero dei dati del prodotto.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}