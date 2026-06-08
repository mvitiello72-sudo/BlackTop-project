package model;

import java.io.Serializable;

public class ElementoCarrello implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Prodotto prodotto;
    private String taglia;
    private int quantita;

    public ElementoCarrello() { }
    
    public ElementoCarrello(Prodotto prodotto, String taglia, int quantita) 
    {
        this.prodotto = prodotto;
        this.taglia = taglia;
        this.quantita = quantita;
    }
    
    // Getter e Setter
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }

    public String getTaglia() { return taglia; }
    public void setTaglia(String taglia) { this.taglia = taglia; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public double getPrezzoTotale()
    {
        if (prodotto == null)
        		return 0.0;
        
        return prodotto.getPrezzo() * quantita;
    }
}