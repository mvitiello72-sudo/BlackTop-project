package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DettagliOrdine implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int fkOrdine;
    private int fkProdotto;
    private int quantita;
    private BigDecimal prezzoSnapshot;

    // Costruttore vuoto
    public DettagliOrdine() { }

    // Costruttore completo
    public DettagliOrdine(int fkOrdine, int fkProdotto,
                           int quantita, BigDecimal prezzoSnapshot)
    {
        this.fkOrdine = fkOrdine;
        this.fkProdotto = fkProdotto;
        this.quantita = quantita;
        this.prezzoSnapshot = prezzoSnapshot;
    }

    // Getter e Setter
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

    public BigDecimal getPrezzoSnapshot() {
        return prezzoSnapshot;
    }

    public void setPrezzoSnapshot(BigDecimal prezzoSnapshot) {
        this.prezzoSnapshot = prezzoSnapshot;
    }
}