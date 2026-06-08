package model;

import java.io.Serializable;

public class Indirizzo implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int idIndirizzo;
    private String viaNumciv;
    private String paese;
    private String citta;
    private String provincia;
    private String codicePostale;
    private boolean predefinito;
    
    private int fkUtente;

    // Costruttore vuoto
    public Indirizzo() { }

    // Getter e Setter
    public int getIdIndirizzo() {
        return idIndirizzo;
    }

    public void setIdIndirizzo(int idIndirizzo) {
        this.idIndirizzo = idIndirizzo;
    }

    public String getViaNumciv() {
        return viaNumciv;
    }

    public void setViaNumciv(String viaNumciv) {
        this.viaNumciv = viaNumciv;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCodicePostale() {
        return codicePostale;
    }

    public void setCodicePostale(String codicePostale) {
        this.codicePostale = codicePostale;
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