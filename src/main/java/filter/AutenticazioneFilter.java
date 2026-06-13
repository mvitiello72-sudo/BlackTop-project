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
	    
	    // Controllo che il token sia in sessione
	    HttpSession session = request.getSession(false);
	    Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;
	    String role = (utente != null) ? utente.getRuolo() : null;
	    
	    // Controllo autenticazione e autorizzazione
	    boolean autorizzato = false;
	    if (role != null)
	    {
	    		if (path.startsWith("/admin/")) {
	            autorizzato = role.equals("ADMIN");
	        } else if (path.startsWith("/common/")) {
	            autorizzato = role.equals("ADMIN") || role.equals("USER");
	        }
	    }
	    if (autorizzato) {
	        chain.doFilter(request, response);
	    } else {
	        response.sendRedirect(request.getContextPath() + "/login");
	    }
	}
}