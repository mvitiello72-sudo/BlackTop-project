package control.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import model.Indirizzo;
import model.MetodoPagamento;
import model.Ordine;

import model.dao.IndirizzoDAO;
import model.dao.MetodoPagamentoDAO;
import model.dao.OrdineDAO;

@WebServlet("/profilo")
public class ProfiloServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private IndirizzoDAO indirizzoDAO;
    private MetodoPagamentoDAO pagamentoDAO;
    private OrdineDAO ordineDAO;

    @Override
    public void init() throws ServletException
    {
        this.indirizzoDAO = new IndirizzoDAO();
        this.pagamentoDAO = new MetodoPagamentoDAO();
        this.ordineDAO = new OrdineDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Utente utenteLoggato = (Utente) session.getAttribute("utente");

        if (utenteLoggato == null)
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try
        {
            int idUtente = utenteLoggato.getIdUtente();

            List<Indirizzo> listaIndirizzi = indirizzoDAO.doRetrieveByUtente(idUtente);

            List<MetodoPagamento> listaCarte = pagamentoDAO.doRetrieveByUtente(idUtente);

            List<Ordine> listaOrdini = ordineDAO.doRetrieveByUtente(idUtente);

            request.setAttribute("indirizzi", listaIndirizzi);
            request.setAttribute("carte", listaCarte);
            request.setAttribute("ordini", listaOrdini);

            request.getRequestDispatcher("/WEB-INF/view/common/profilo.jsp")
                   .forward(request, response);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Errore nel caricamento del profilo");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }
}