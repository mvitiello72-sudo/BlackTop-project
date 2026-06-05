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
    
    private int fkUtente;

    // Costruttore vuoto
    public Indirizzo() { }

    // Costruttore completo
    public Indirizzo(int idIndirizzo, String viaNumciv, String paese, String citta,
                     String provincia, String codicePostale, int fkUtente)
    {
        this.idIndirizzo = idIndirizzo;
        this.viaNumciv = viaNumciv;
        this.paese = paese;
        this.citta = citta;
        this.provincia = provincia;
        this.codicePostale = codicePostale;
        this.fkUtente = fkUtente;
    }

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
}