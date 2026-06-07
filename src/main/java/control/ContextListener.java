package control;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import model.connection.ConnectionPool;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener 
{
    @Override
    public void contextInitialized(ServletContextEvent sce) 
    {
        System.out.println("DEBUG - Tomcat si sta avviando: Inizializzo il Connection Pool...");
        try {
            // Accendiamo il pool con 10 connessioni iniziali
            ConnectionPool.init(10); 
            System.out.println("DEBUG - Connection Pool inizializzato con successo!");
        } catch (SQLException e) {
            System.err.println("DEBUG - ERRORE CRITICO: Impossibile inizializzare il Connection Pool!");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) 
    {
        System.out.println("DEBUG - Tomcat si sta spegnendo: Chiudo il Connection Pool...");
        ConnectionPool.shutdown();
        System.out.println("DEBUG - Connection Pool chiuso e risorse rilasciate.");
    }
}