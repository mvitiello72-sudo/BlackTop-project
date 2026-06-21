package control.common;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/contatti")
public class ContattaciServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Mostra semplicemente la pagina del form
        request.getRequestDispatcher("/WEB-INF/view/common/contatti.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String messaggio = request.getParameter("messaggio");

        // Validazione base
        if (nome == null || email == null || messaggio == null || nome.trim().isEmpty() || email.trim().isEmpty() || messaggio.trim().isEmpty()) {
            request.setAttribute("errore", "Tutti i campi sono obbligatori.");
            request.getRequestDispatcher("/WEB-INF/view/common/contatti.jsp").forward(request, response);
            return;
        }

        //simuliamo il successo impostando un messaggio di conferma.
        request.setAttribute("successo", "Grazie " + nome + ", il tuo messaggio è stato inviato con successo! Ti risponderemo al più presto.");
        
        request.getRequestDispatcher("/WEB-INF/view/common/contatti.jsp").forward(request, response);
    }
}