package model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Carrello implements Serializable
{
    private static final long serialVersionUID = 1L;

    private List<ElementoCarrello> elementi;
 
    public Carrello() 
    { 
        this.elementi = new ArrayList<>();
    }

    public List<ElementoCarrello> getElementi()
    {
        return elementi;
    }

    public void aggiungiProdotto(Prodotto p, String taglia, int quantita)
    {
        for (ElementoCarrello item : elementi)
        {
        		//se aggiunge un prodotto già esistente, e con la stessa taglia, aumenta solo la quantità
            if (item.getProdotto().getIdProdotto() == p.getIdProdotto() && item.getTaglia().equals(taglia))
            {
                item.setQuantita(item.getQuantita() + quantita);
                return;
            }
        }      
        elementi.add(new ElementoCarrello(p, taglia, quantita));
    }

    	//rimuove un prodotto specifico con quella determinata taglia
    public void rimuoviProdotto(int idProdotto, String taglia)
    {
        elementi.removeIf(item -> item.getProdotto().getIdProdotto() == idProdotto && item.getTaglia().equals(taglia));
    }
    
    public void aggiornaQuantita(int idProdotto, String taglia, int nuovaQuantita) 
    {
        for (ElementoCarrello item : elementi) 
        {
            if (item.getProdotto().getIdProdotto() == idProdotto && item.getTaglia().equals(taglia)) 
            {
                // Controllo di sicurezza: modifichiamo solo se la quantità è valida (maggiore di zero)
                if (nuovaQuantita > 0) 
                {
                    item.setQuantita(nuovaQuantita);
                }
                return; // Trovato e aggiornato, usciamo dal metodo
            }
        }
    }

    	//calcola il totale complessivo del carrello
    public double getTotaleComplessivo()
    {
        double totale = 0;
        for (ElementoCarrello item : elementi)
        {
            totale = totale + item.getPrezzoTotale();
        }
        return totale;
    }
}