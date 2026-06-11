package control.common;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import model.dao.MetodoPagamentoDAO;

@WebServlet("/common/impostaPagamentoPredefinito")
public class ImpostaMetodoPagamentoPredefinitoServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
       
    private MetodoPagamentoDAO pagamentoDAO;

    @Override
    public void init() throws ServletException
    {
        this.pagamentoDAO = new MetodoPagamentoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.sendRedirect(request.getContextPath() + "/common/profilo");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Utente utenteLoggato = (Utente) session.getAttribute("utente");

        String idPagamentoStr = request.getParameter("idMetodo");

        if (idPagamentoStr != null && !idPagamentoStr.trim().isEmpty())
        {
            try {
                int idMetodo = Integer.parseInt(idPagamentoStr);
                int idUtente = utenteLoggato.getIdUtente();

                pagamentoDAO.cambiaStatoPredefinito(idUtente, idMetodo);
                
                session.setAttribute("messaggioSuccesso", "Metodo di pagamento predefinito aggiornato!");

            } catch (NumberFormatException e) {
                System.err.println("ID Metodo Pagamento non valido: " + idPagamentoStr);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Errore SQL durante il cambio del pagamento predefinito: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile aggiornare il metodo di pagamento predefinito.");
                return;
            }
        }

        // 4. Rimbalzo sulla ProfiloServlet per rinfrescare la pagina con le modifiche visibili
        response.sendRedirect(request.getContextPath() + "/common/profilo");
    }
}