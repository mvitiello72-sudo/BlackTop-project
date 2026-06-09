package control.common;

import model.Utente;
import model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException 
    {
        this.utenteDAO = new UtenteDAO();
    }

    // 1. mostra la pag di login
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        
        if (session.getAttribute("utente") != null) 
        {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/view/common/login.jsp").forward(request, response);
    }

    // 2. ELABORA IL TENTATIVO DI ACCESSO
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) 
        {
            request.setAttribute("errore", "Inserisci sia l'email che la password.");
            request.getRequestDispatcher("/WEB-INF/view/common/login.jsp").forward(request, response);
            return;
        }

        try 
        {
            Utente utente = utenteDAO.doRetrieveByEmail(email);
            
            if (utente != null && utente.getPassword().equals(password)) 
            {
                // Credenziali corrette: creiamo o recuperiamo la sessione corrente
                HttpSession session = request.getSession();
                session.setAttribute("utente", utente);

                response.sendRedirect(request.getContextPath() + "/home");
            } 
            else 
            {
                request.setAttribute("errore", "Email o password errati. Riprova.");
                request.getRequestDispatcher("/WEB-INF/view/common/login.jsp").forward(request, response);
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Errore SQL durante il login utente: " + e.getMessage());
            request.setAttribute("errore", "Si è verificato un errore tecnico di connessione. Riprova più tardi.");
            request.getRequestDispatcher("/WEB-INF/view/common/login.jsp").forward(request, response);
        }
    }
}