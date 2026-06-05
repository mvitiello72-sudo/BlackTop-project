package model;

import java.io.Serializable;
import java.sql.Date;

public class MetodoPagamento implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int idMetodo;
    private String tipo;            // es: "Carta", "PayPal", "Bonifico"
    private String numeroCarta;
    private String intestatario;
    private Date scadenza;
    private String cvv;

    private int fkUtente;

    // Costruttore vuoto
    public MetodoPagamento() { }

    // Costruttore completo
    public MetodoPagamento(int idMetodo, String tipo, String numeroCarta,
                           String intestatario, Date scadenza, String cvv,
                           int fkUtente)
    {
        this.idMetodo = idMetodo;
        this.tipo = tipo;
        this.numeroCarta = numeroCarta;
        this.intestatario = intestatario;
        this.scadenza = scadenza;
        this.cvv = cvv;
        this.fkUtente = fkUtente;
    }

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

    @Override
    public String toString() {
        return "MetodoPagamento{" +
                "idMetodo=" + idMetodo +
                ", tipo='" + tipo + '\'' +
                ", numeroCarta='" + numeroCarta + '\'' +
                ", intestatario='" + intestatario + '\'' +
                ", scadenza=" + scadenza +
                ", cvv='" + cvv + '\'' +
                ", fkUtente=" + fkUtente +
                '}';
    }
}