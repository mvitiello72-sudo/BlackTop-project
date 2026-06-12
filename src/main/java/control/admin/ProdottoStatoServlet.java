package control.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import model.dao.ProdottoDAO;

@WebServlet("/admin/prodottoStato")
public class ProdottoStatoServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    private ProdottoDAO prodottoDAO = new ProdottoDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		HttpSession session = request.getSession();

        String idParam = request.getParameter("id");
        String action = request.getParameter("action");

        try
        {
            int idProdotto = Integer.parseInt(idParam);
            boolean nuovoStato = "attiva".equalsIgnoreCase(action);
            
            prodottoDAO.updateAttivo(idProdotto, nuovoStato);

            session.setAttribute("successMessage", "Stato del prodotto aggiornato con successo!");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Errore durante la modifica dello stato: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=prodotti");    
    }
}
