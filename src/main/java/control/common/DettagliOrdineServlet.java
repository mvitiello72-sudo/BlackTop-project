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
import model.Ordine;
import model.Prodotto;
import model.DettagliOrdine;

import model.dao.OrdineDAO;
import model.dao.DettagliOrdineDAO;
import model.dao.ProdottoDAO;

@WebServlet("/common/dettaglioOrdine")
public class DettagliOrdineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private OrdineDAO ordineDAO;
    private DettagliOrdineDAO dettagliOrdineDAO;
    private ProdottoDAO prodottoDAO;

    @Override
    public void init() throws ServletException {
        ordineDAO = new OrdineDAO();
        dettagliOrdineDAO = new DettagliOrdineDAO();
        prodottoDAO = new ProdottoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Utente utenteLoggato = (Utente) session.getAttribute("utente");


        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty())
        {
            response.sendRedirect(request.getContextPath() + "/profilo");
            return;
        }

        try
        {
            int idOrdine = Integer.parseInt(idParam);

            // ORDINE
            Ordine ordine = ordineDAO.doRetrieveByKey(idOrdine);

            if (ordine == null)
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (ordine.getFkUtente() != utenteLoggato.getIdUtente())
            {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // DETTAGLI ORDINE
            List<DettagliOrdine> dettagli = dettagliOrdineDAO.doRetrieveByOrdine(idOrdine);

            for (DettagliOrdine d : dettagli)
            {
            		//recupera il prodotto
                Prodotto prodotto = prodottoDAO.doRetrieveByKey(d.getFkProdotto());

                d.setProdotto(prodotto);
            }

            // ATTRIBUTI JSP
            request.setAttribute("ordine", ordine);
            request.setAttribute("dettagli", dettagli);

            request.getRequestDispatcher("/WEB-INF/view/common/dettaglioOrdine.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/common/profilo");
        } catch (SQLException e) {
            throw new ServletException("Errore nel recupero dettaglio ordine", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}