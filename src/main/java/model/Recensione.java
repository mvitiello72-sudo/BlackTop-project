package model;

import java.io.Serializable;
import java.sql.Date;

public class Recensione implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int fkUtente;
    private int fkProdotto;
    private int voto;              // 1-5
    private String commento;
    private Date dataRecensione;

    // Costruttore vuoto
    public Recensione() { }

    // Costruttore completo
    public Recensione(int fkUtente, int fkProdotto,
                      int voto, String commento, Date dataRecensione)
    {
        this.fkUtente = fkUtente;
        this.fkProdotto = fkProdotto;
        this.voto = voto;
        this.commento = commento;
        this.dataRecensione = dataRecensione;
    }

    // Getter e Setter
    public int getFkUtente() {
        return fkUtente;
    }

    public void setFkUtente(int fkUtente) {
        this.fkUtente = fkUtente;
    }

    public int getFkProdotto() {
        return fkProdotto;
    }

    public void setFkProdotto(int fkProdotto) {
        this.fkProdotto = fkProdotto;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public Date getDataRecensione() {
        return dataRecensione;
    }

    public void setDataRecensione(Date dataRecensione) {
        this.dataRecensione = dataRecensione;
    }
}