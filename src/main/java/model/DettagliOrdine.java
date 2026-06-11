package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DettagliOrdine implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int fkOrdine;
    private int fkProdotto;
    private int quantita;
    private double prezzoSnapshot;
    
    private Prodotto prodotto;

    // Costruttore vuoto
    public DettagliOrdine() { }

    // Getter e Setter
    
    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
    
    public int getFkOrdine() {
        return fkOrdine;
    }

    public void setFkOrdine(int fkOrdine) {
        this.fkOrdine = fkOrdine;
    }

    public int getFkProdotto() {
        return fkProdotto;
    }

    public void setFkProdotto(int fkProdotto) {
        this.fkProdotto = fkProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getPrezzoSnapshot() {
        return prezzoSnapshot;
    }

    public void setPrezzoSnapshot(double prezzoSnapshot) {
        this.prezzoSnapshot = prezzoSnapshot;
    }
}