package model;

import java.io.Serializable;
import java.sql.Date;

public class MetodoPagamento implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int idMetodo;
    private String tipo;      
    private String numeroCarta;
    private String intestatario;
    private Date scadenza;
    private String cvv;
    private boolean predefinito;

    private int fkUtente;

    // Costruttore vuoto
    public MetodoPagamento() { }

    // Getter e Setter
    public int getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(int idMetodo) {
        this.idMetodo = idMetodo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public String getIntestatario() {
        return intestatario;
    }

    public void setIntestatario(String intestatario) {
        this.intestatario = intestatario;
    }

    public Date getScadenza() {
        return scadenza;
    }

    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public int getFkUtente() {
        return fkUtente;
    }

    public void setFkUtente(int fkUtente) {
        this.fkUtente = fkUtente;
    }
    
    public boolean getPredefinito() {
        return predefinito;
    }

    public void setPredefinito(boolean predefinito) {
        this.predefinito = predefinito;
    }

}