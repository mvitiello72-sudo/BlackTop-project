package model;

import java.io.Serializable;
import java.sql.Date;

public class Ordine implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int idOrdine;
    private Date dataOrdine;
    private String stato;
    private double totale;

    private int fkUtente;
    private int fkIndirizzo;
    private int fkPagamento;
    
    private Utente utente;

    // Costruttore vuoto
    public Ordine() { }

    // Getter e Setter
    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    
    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public Date getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Date dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public int getFkUtente() {
        return fkUtente;
    }

    public void setFkUtente(int fkUtente) {
        this.fkUtente = fkUtente;
    }

    public int getFkIndirizzo() {
        return fkIndirizzo;
    }

    public void setFkIndirizzo(int fkIndirizzo) {
        this.fkIndirizzo = fkIndirizzo;
    }
    
    public int getFkPagamento() {
        return fkPagamento;
    }

    public void setFkPagamento(int fkPagamento) {
        this.fkPagamento = fkPagamento;
    }
}