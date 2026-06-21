package control.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.Prodotto;
import model.Utente;
import model.Immagine;
import model.dao.ProdottoDAO;
import model.dao.ImmagineDAO;

@WebServlet("/admin/modificaProdotto")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB massimo per singolo file
    maxRequestSize = 1024 * 1024 * 50     // 50MB massimo per l'intero form
)
public class ModificaProdottoServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private final ProdottoDAO prodottoDAO = new ProdottoDAO();
    private final ImmagineDAO immagineDAO = new ImmagineDAO();

    private static final String UPLOAD_DIR_WEB = "img/prodotti";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty())
        {
            response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=prodotti");
            return;
        }

        try
        {
            int idProdotto = Integer.parseInt(idParam);
            Prodotto prodotto = prodottoDAO.doRetrieveByKey(idProdotto); 

            if (prodotto != null)
            {
                request.setAttribute("prodotto", prodotto);
                request.getRequestDispatcher("/WEB-INF/view/admin/modificaProdotto.jsp").forward(request, response);
            } else {
                session.setAttribute("errorMessage", "Prodotto non trovato.");
                response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=prodotti");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");
        int idProdotto = Integer.parseInt(idParam);

        // CASO 1: CANCELLAZIONE DI UNA FOTO DELLA GALLERIA
        if ("deleteImage".equals(action))
        {
            String idImgParam = request.getParameter("idImmagine");
            try
            {
                int idImmagine = Integer.parseInt(idImgParam);
                Immagine img = immagineDAO.doRetrieveByKey(idImmagine);
                
                if (img != null)
                {
                    String appPath = request.getServletContext().getRealPath("");
                    String percorsoDb = img.getPercorsoImmagine();
                    
                    if (percorsoDb.startsWith("/")) {
                        percorsoDb = percorsoDb.substring(1);
                    }
                    
                    String percorsoFisicoImg = percorsoDb.replace("/", File.separator);
                    File fileFisico = new File(appPath + File.separator + percorsoFisicoImg);
                    
                    // DEBUG LOG per monitorare la cancellazione sul server
                    System.out.println("[DELETE] Tentativo di eliminazione: " + fileFisico.getAbsolutePath());
                    
                    if (fileFisico.exists())
                    {
                        boolean cancellato = fileFisico.delete();
                        System.out.println("[DELETE] File eliminato dal disco? " + cancellato);
                    } else {
                        System.out.println("[DELETE] ATTENZIONE: File non trovato sul disco.");
                    }
                    
                    immagineDAO.doDelete(idImmagine);
                    session.setAttribute("successMessage", "Immagine rimossa correttamente!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("errorMessage", "Errore del database durante la rimozione dell'immagine.");
            }
            response.sendRedirect(request.getContextPath() + "/admin/modificaProdotto?id=" + idProdotto);
            return;
        }

        // CASO 2: AGGIORNAMENTO DATI PRODOTTO E ACQUISIZIONE EVENTUALE NUOVA FOTO
        try
        {
            String nome = request.getParameter("nome");
            String squadra = request.getParameter("squadra");
            String materiale = request.getParameter("materiale");
            String descrizione = request.getParameter("descrizione");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String taglia = request.getParameter("taglia");
            boolean attivo = request.getParameter("attivo") != null;
            String categoria = request.getParameter("categoria");
            
            Prodotto p = new Prodotto();
            p.setIdProdotto(idProdotto);
            p.setNome(nome);
            p.setSquadra(squadra);
            p.setMateriale(materiale);
            p.setDescrizione(descrizione);
            p.setPrezzo(prezzo);
            p.setStock(stock);
            p.setTaglia(taglia);
            p.setAttivo(attivo);
            p.setCategoria(categoria);
            
            prodottoDAO.doUpdate(p);

            Part filePart = request.getPart("nuovaImmagine"); 
            if (filePart != null && filePart.getSize() > 0)
            {
                String appPath = request.getServletContext().getRealPath("");
                String percorsoSalvataggioFisico = appPath + File.separator + "img" + File.separator + "prodotti";
                
                File fileSaveDir = new File(percorsoSalvataggioFisico);
                if (!fileSaveDir.exists())
                {
                    fileSaveDir.mkdirs(); 
                }
                
                String nomeFileOriginale = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String fileName = idProdotto + "_" + System.currentTimeMillis() + "_" + nomeFileOriginale;
                
                String pathCompletoFileFisico = percorsoSalvataggioFisico + File.separator + fileName;
                filePart.write(pathCompletoFileFisico);
                
                Immagine nuovaImg = new Immagine();
                nuovaImg.setPercorsoImmagine(UPLOAD_DIR_WEB + "/" + fileName);
                nuovaImg.setFkProdotto(idProdotto);
                
                immagineDAO.doSave(nuovaImg);
            }

            session.setAttribute("successMessage", "Prodotto aggiornato con successo!");
            response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=prodotti");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Errore: formato dei dati numerici inseriti non valido.");
            ricaricaFormErrore(idParam, request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore interno del database durante il salvataggio.");
            ricaricaFormErrore(idParam, request, response);
        }
    }

    private void ricaricaFormErrore(String idParam, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        try {
            int idProdotto = Integer.parseInt(idParam);
            Prodotto prodotto = prodottoDAO.doRetrieveByKey(idProdotto);
            request.setAttribute("prodotto", prodotto);
            request.getRequestDispatcher("/WEB-INF/view/admin/modificaProdotto.jsp").forward(request, response);
        } catch (Exception ex) {
            response.sendRedirect(request.getContextPath() + "/admin/admindashboard?tab=prodotti");
        }
    }
}