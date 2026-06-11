package control.admin;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Prodotto;
import model.Utente;
import model.dao.ProdottoDAO;

@WebServlet("/aggiungiProdotto")
public class AggiungiProdottoServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private final ProdottoDAO prodottoDAO = new ProdottoDAO();

    // 1. Mostra il form di inserimento prodotto
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        // Controllo di sicurezza
        if (utente == null || !"ADMIN".equals(utente.getRuolo()))
        {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Non hai i permessi per accedere a questa pagina.");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/view/admin/aggiungiProdotto.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        
        if (utente == null || !"ADMIN".equals(utente.getRuolo()))
        {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Non hai i permessi per eseguire questa operazione.");
            return;
        }
        
        // Imposta la codifica dei caratteri corretta per evitare problemi con accenti
        request.setCharacterEncoding("UTF-8");

        try
        {
            // Recupero dei parametri dal form HTML
            String nome = request.getParameter("nome");
            String squadra = request.getParameter("squadra");
            String materiale = request.getParameter("materiale");
            String descrizione = request.getParameter("descrizione");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String taglia = request.getParameter("taglia");
            
            // Gestione del checkbox/stato attivo (se non selezionato, i browser non lo inviano)
            boolean attivo = request.getParameter("attivo") != null;
            
            int sconto = 0;
            String scontoParam = request.getParameter("sconto");
            if (scontoParam != null && !scontoParam.trim().isEmpty()) 
            {
                sconto = Integer.parseInt(scontoParam);
            }
            
            String categoria = request.getParameter("categoria");
            
            Prodotto p = new Prodotto();
            p.setNome(nome);
            p.setSquadra(squadra);
            p.setMateriale(materiale);
            p.setDescrizione(descrizione);
            p.setPrezzo(prezzo);
            p.setStock(stock);
            p.setTaglia(taglia);
            p.setAttivo(attivo);
            p.setSconto(sconto);
            p.setCategoria(categoria);

            prodottoDAO.doSave(p);

            session.setAttribute("successMessage", "Prodotto aggiunto con successo al catalogo!");
            
            response.sendRedirect(request.getContextPath() + "/admindashboard?tab=prodotti");

        }
        catch (NumberFormatException e)
        {
            request.setAttribute("errorMessage", "Errore: Controlla che i campi Prezzo, Stock e Sconto siano numeri validi.");
            request.getRequestDispatcher("/WEB-INF/view/admin/aggiungiProdotto.jsp").forward(request, response);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore del database durante il salvataggio del prodotto.");
            request.getRequestDispatcher("/WEB-INF/view/admin/aggiungiProdotto.jsp").forward(request, response);
        }
    }
}