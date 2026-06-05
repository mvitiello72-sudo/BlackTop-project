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

    // Costruttore vuoto
    public Ordine() { }

    // Costruttore completo
    public Ordine(int idOrdine, Date dataOrdine, String stato,
                   double totale, int fkUtente, int fkIndirizzo)
    {
        this.idOrdine = idOrdine;
        this.dataOrdine = dataOrdine;
        this.stato = stato;
        this.totale = totale;
        this.fkUtente = fkUtente;
        this.fkIndirizzo = fkIndirizzo;
    }

    // Getter e Setter
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
}