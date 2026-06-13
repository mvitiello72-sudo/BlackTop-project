package control.common;

import model.Utente;
import model.dao.UtenteDAO;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/verificaEmail")
public class VerificaEmailServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException
    {
        this.utenteDAO = new UtenteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String email = request.getParameter("email");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        JSONObject jsonRisposta = new JSONObject();

        if (email == null || email.trim().isEmpty())
        {
            jsonRisposta.put("esiste", false);
            out.print(jsonRisposta.toString());
            return;
        }

        try
        {
            Utente utente = utenteDAO.doRetrieveByEmail(email.trim());
            
            if (utente != null)
            {
                jsonRisposta.put("esiste", true);  // Email già occupata
            }
            else
            {
                jsonRisposta.put("esiste", false); // Email libera
            }
        } catch (Exception e) {
            log("Errore nella verifica email", e);
            jsonRisposta.put("esiste", false); // In caso di errore non blocchiamo l'utente
        }

        out.print(jsonRisposta.toString());
    }
}