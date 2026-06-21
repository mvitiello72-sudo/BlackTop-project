package control.common;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import model.MetodoPagamento;
import model.dao.MetodoPagamentoDAO;

@WebServlet("/common/aggiungiPagamento")
public class AggiungiMetodoPagamentoServlet extends HttpServlet
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

        String tipo = request.getParameter("tipo");
        String intestatario = request.getParameter("intestatario");
        String numeroCarta = request.getParameter("numero_carta");
        String scadenzaStr = request.getParameter("scadenza"); // Arriva come stringa "YYYY-MM-DD"
        String cvv = request.getParameter("cvv");
        
        String predefinitoStr = request.getParameter("predefinito");
        boolean predefinito = "true".equals(predefinitoStr);

        if (tipo != null && !tipo.trim().isEmpty() &&
            intestatario != null && !intestatario.trim().isEmpty() &&
            numeroCarta != null && !numeroCarta.trim().isEmpty() &&
            scadenzaStr != null && !scadenzaStr.trim().isEmpty() &&
            cvv != null && !cvv.trim().isEmpty())
        {
            try
            {
                MetodoPagamento nuovaCarta = new MetodoPagamento();
                nuovaCarta.setTipo(tipo.trim());
                nuovaCarta.setIntestatario(intestatario.trim());
                nuovaCarta.setNumeroCarta(numeroCarta.trim());
                nuovaCarta.setCvv(cvv.trim());
                nuovaCarta.setPredefinito(predefinito);
                
                // Conversione della stringa in java.sql.Date per il DB
                Date dataScadenza = Date.valueOf(scadenzaStr);
                nuovaCarta.setScadenza(dataScadenza);
                
                nuovaCarta.setFkUtente(utenteLoggato.getIdUtente());
                pagamentoDAO.doSave(nuovaCarta);
                
                session.setAttribute("messaggioSuccesso", "Metodo di pagamento salvato con successo!");

            } catch (IllegalArgumentException e) {
                System.err.println("Formato data scadenza non valido: " + scadenzaStr);
                session.setAttribute("messaggioErrore", "La data di scadenza inserita non è valida o ha un formato errato.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Errore SQL durante il salvataggio del metodo di pagamento: " + e.getMessage());
                session.setAttribute("messaggioErrore", "Errore interno del database durante il salvataggio della carta.");
            }
        } else {
            session.setAttribute("messaggioErrore", "Tutti i campi del modulo sono obbligatori.");
        }
        
        response.sendRedirect(request.getContextPath() + "/common/profilo");
    }
}