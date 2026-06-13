package control.common; 

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Ordine;
import model.Fattura;
import model.dao.OrdineDAO;
import model.DettagliOrdine;
import model.dao.DettagliOrdineDAO;
import model.Utente;
import model.Indirizzo;
import model.dao.IndirizzoDAO;
import model.dao.FatturaDAO;


// Import della libreria iText PDF
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/common/fattura")
public class FatturaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private OrdineDAO ordineDAO;
    private DettagliOrdineDAO dettaglioDAO;
    private IndirizzoDAO indirizzoDAO;
    private FatturaDAO fatturaDAO;


    @Override
    public void init() throws ServletException
    {
        this.ordineDAO = new OrdineDAO();
        this.dettaglioDAO = new DettagliOrdineDAO();
        this.indirizzoDAO = new IndirizzoDAO();
        fatturaDAO = new FatturaDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession(false);
        Utente utente = (Utente) session.getAttribute("utente");
        gestisciFattura(request, response, utente);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void gestisciFattura(HttpServletRequest request, HttpServletResponse response, Utente utente) throws ServletException, IOException
    {
        String idOrdineStr = request.getParameter("id"); 
        
        if (idOrdineStr == null || idOrdineStr.isEmpty())
        {
            response.sendRedirect(request.getContextPath() + "/common/profilo?view=ordini");
            return;
        }
        
        try
        {
            int idOrdine = Integer.parseInt(idOrdineStr);
            
            Ordine ordine = ordineDAO.doRetrieveByKey(idOrdine);
            if (ordine == null)
            {
                response.sendRedirect(request.getContextPath() + "/common/profilo?view=ordini");
                return;
            }
            
            Fattura fattura = fatturaDAO.doRetrieveByOrdine(idOrdine);
            if (fattura == null)
            {
                response.sendRedirect(request.getContextPath() + "/common/profilo?view=ordini");
                return;
            }
            
            int idIndirizzo = ordine.getFkIndirizzo(); 
            Indirizzo indirizzo = indirizzoDAO.doRetrieveByKey(idIndirizzo);
            if (indirizzo == null)
            {
                response.sendRedirect(request.getContextPath() + "/common/profilo?view=ordini");
                return;
            }
            
            List<DettagliOrdine> dettagli = dettaglioDAO.doRetrieveByOrdine(idOrdine);
            if (dettagli == null)
            {
                response.sendRedirect(request.getContextPath() + "/common/profilo?view=ordini");
                return;
            }
            
            // Configura la risposta HTTP per il download del PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"fattura_ordine_" + idOrdine + ".pdf\"");
            
            // Genera la struttura del documento iText
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            
            // Configurazione Font
            Font titoloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
            Font smallFont = new Font(Font.FontFamily.HELVETICA, 9,  Font.NORMAL);
            
            // Intestazione PDF
            Paragraph titolo = new Paragraph("FATTURA", titoloFont);
            titolo.setAlignment(Element.ALIGN_CENTER);
            document.add(titolo);
            
            document.add(Chunk.NEWLINE);
            
            // Dati fiscali reali presi dalla tabella fattura del DB
            document.add(new Paragraph("FATTURA NUMERO: FAT-" + fattura.getIdFattura(), boldFont));
            document.add(new Paragraph("Data Emissione: " + fattura.getDataEmissione(), normalFont)); 
            document.add(new Paragraph("Riferimento Ordine: #" + idOrdine + " del " + ordine.getDataOrdine(), smallFont)); 
            
            document.add(Chunk.NEWLINE);
            
            // Dati del cliente
            document.add(new Paragraph("Dati cliente", boldFont));
            document.add(new Paragraph(utente.getNome() + " " + utente.getCognome(), normalFont));
            document.add(new Paragraph(utente.getEmail(), normalFont));
            
            // Dati indirizzo
            document.add(new Paragraph(
                    indirizzo.getViaNumciv() + ", " +
                    indirizzo.getCitta() + " (" +
                    indirizzo.getProvincia() + ") " +
                    indirizzo.getCodicePostale(), normalFont));
            
            document.add(Chunk.NEWLINE);
            
            // Creazione Tabella Prodotti (4 colonne)
            PdfPTable tabella = new PdfPTable(4);
            tabella.setWidthPercentage(100);
            tabella.setWidths(new float[]{4f, 1f, 2f, 2f});
            
            // Intestazioni della tabella
            for (String intestazione : new String[]{"Prodotto", "Qtà", "Prezzo unitario", "Subtotale"})
            {
                PdfPCell cella = new PdfPCell(new Phrase(intestazione, boldFont));
                cella.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cella.setPadding(6);
                tabella.addCell(cella);
            }
            
            // Popolamento righe della tabella dai dettagli
            for (DettagliOrdine d : dettagli)
            {
                tabella.addCell(new Phrase(d.getProdotto().getNome(), normalFont));
                tabella.addCell(new Phrase(String.valueOf(d.getQuantita()), normalFont));
                tabella.addCell(new Phrase(String.format("%.2f €", d.getPrezzoSnapshot()), normalFont));
                tabella.addCell(new Phrase(String.format("%.2f €", d.getPrezzoSnapshot() * d.getQuantita()), normalFont));
            }
            
            document.add(tabella);
            
            document.add(Chunk.NEWLINE);
            
            // Riga del Totale finale
            Paragraph totale = new Paragraph("Totale: " + String.format("%.2f €", fattura.getTotaleFattura()), boldFont);
            totale.setAlignment(Element.ALIGN_RIGHT);
            document.add(totale);
            
            document.add(Chunk.NEWLINE);

            Paragraph piede = new Paragraph("Grazie per il tuo acquisto — BlackTop", smallFont);
            piede.setAlignment(Element.ALIGN_CENTER);
            document.add(piede);
            
            // Chiusura e output stream inviato al browser
            document.close();
            
        } catch (Exception e) {
            throw new ServletException("Errore generazione fattura: " + e.getMessage(), e);
        }
    }
}