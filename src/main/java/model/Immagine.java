package model;

import java.io.Serializable;

public class Immagine implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int idImmagine;
    private String percorsoImmagine;

    private int fkProdotto;

    // Costruttore vuoto
    public Immagine() { }

    // Getter e Setter
    public int getIdImmagine() {
        return idImmagine;
    }

    public void setIdImmagine(int idImmagine) {
        this.idImmagine = idImmagine;
    }

    public String getPercorsoImmagine() {
        return percorsoImmagine;
    }

    public void setPercorsoImmagine(String percorsoImmagine) {
        this.percorsoImmagine = percorsoImmagine;
    }

    public int getFkProdotto() {
        return fkProdotto;
    }

    public void setFkProdotto(int fkProdotto) {
        this.fkProdotto = fkProdotto;
    }
}