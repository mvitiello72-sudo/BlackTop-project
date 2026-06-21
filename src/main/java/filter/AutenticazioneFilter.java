package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Utente;

// Configurato per proteggere solo gli URL specificati
@WebFilter("/*")
public class AutenticazioneFilter extends HttpFilter
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	        throws IOException, ServletException
	{
	    String path = request.getServletPath();
	    	// Se l'URL non è protetto, lascia passare
	    if (!path.startsWith("/admin/") && !path.startsWith("/common/"))
	    {
	    		chain.doFilter(request, response);
	        return; // Per evitare che il codice che segue venga eseguito
	    }
	    
	    //recupera la sessione solo se esiste
	    HttpSession session = request.getSession(false);
	    //se la sessione esisteva, prende la sessione dell'utente
	    Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;
	    //se l'utente è diverso da null, prendi il suo ruolo
	    String role = (utente != null) ? utente.getRuolo() : null;
	    
	    // Controllo autenticazione e autorizzazione
	    boolean autorizzato = false;
	    if (role != null)
	    {
	    		// Verifica se l'URL richiesto inizia con "/admin/"
	    		if (path.startsWith("/admin/"))
	    		{
	    			//re il ruolo dell'utente era "ADMIN", autorizzato sarà true, false altrimenti
	            autorizzato = role.equals("ADMIN");
	        }
	    		else if (path.startsWith("/common/"))
	    		{
	            autorizzato = role.equals("ADMIN") || role.equals("USER");
	        }
	    }
	    
	    if (autorizzato)
	    {
	        chain.doFilter(request, response);
	    }
	    else
	    {
	    		// L'utente NON è autorizzato. Distinguiamo due casi:
	        if (utente == null)
	        {
	            // CASO 1: L'utente non è loggato affatto -> Vai al Login
	            response.sendRedirect(request.getContextPath() + "/login");
	        }
	        else 
	        {
	            // CASO 2: L'utente È LOGGATO (es. ruolo USER) ma prova a entrare in /admin/
	            // Lanciamo il codice 403
	            response.sendError(HttpServletResponse.SC_FORBIDDEN);
	        }
	    }
	}
}