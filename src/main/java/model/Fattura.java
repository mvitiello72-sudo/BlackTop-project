package model;

import java.io.Serializable;
import java.sql.Date;

public class Fattura implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int idFattura;
    private Date dataEmissione;
    private double totaleFattura;

    private int fkOrdine;

    // Costruttore vuoto
    public Fattura() { }

    // Costruttore completo
    public Fattura(int idFattura, Date dataEmissione,
                    double totaleFattura, int fkOrdine)
    {
        this.idFattura = idFattura;
        this.dataEmissione = dataEmissione;
        this.totaleFattura = totaleFattura;
        this.fkOrdine = fkOrdine;
    }

    // Getter e Setter
    public int getIdFattura() {
        return idFattura;
    }

    public void setIdFattura(int idFattura) {
        this.idFattura = idFattura;
    }

    public Date getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(Date dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public double getTotaleFattura() {
        return totaleFattura;
    }

    public void setTotaleFattura(double totaleFattura) {
        this.totaleFattura = totaleFattura;
    }

    public int getFkOrdine() {
        return fkOrdine;
    }

    public void setFkOrdine(int fkOrdine) {
        this.fkOrdine = fkOrdine;
    }
}