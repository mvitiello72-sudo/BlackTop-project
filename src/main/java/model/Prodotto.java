package model;

import java.io.Serializable;

public class Prodotto implements Serializable
{
	private static final long serialVersionUID = 1L;

    private int idProdotto;
    private String nome;
    private String squadra;
    private String materiale;
    private String descrizione;
    private double prezzo;
    private int stock;
    private String taglia;   // XS, S, M, L, XL, XXL
    private boolean attivo;
    private int sconto;

    // Costruttore vuoto 
    public Prodotto() { }

    // Costruttore completo
    public Prodotto(int idProdotto, String nome, String squadra, String materiale,
                    String descrizione, double prezzo, int stock,
                    String taglia, boolean attivo, int sconto)
    {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.squadra = squadra;
        this.materiale = materiale;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.stock = stock;
        this.taglia = taglia;
        this.attivo = attivo;
        this.sconto = sconto;
    }

    // Getter e Setter
    public int getIdProdotto()
    {
        return this.idProdotto;
    }
    public void setIdProdotto(int idProdotto)
    {
        this.idProdotto = idProdotto;
    }

    public String getNome()
    {
        return this.nome;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    public String getSquadra()
    {
        return this.squadra;
    }
    public void setSquadra(String squadra)
    {
        this.squadra = squadra;
    }

    public String getMateriale()
    {
        return this.materiale;
    }
    public void setMateriale(String materiale)
    {
        this.materiale = materiale;
    }

    public String getDescrizione()
    {
        return this.descrizione;
    }
    public void setDescrizione(String descrizione)
    {
        this.descrizione = descrizione;
    }

    public double getPrezzo()
    {
        return this.prezzo;
    }
    public void setPrezzo(double prezzo)
    {
        this.prezzo = prezzo;
    }

    public int getStock()
    {
        return this.stock;
    }
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    public String getTaglia()
    {
        return this.taglia;
    }
    public void setTaglia(String taglia)
    {
        this.taglia = taglia;
    }

    public boolean isAttivo()
    {
        return this.attivo;
    }
    public void setAttivo(boolean attivo)
    {
        this.attivo = attivo;
    }

    public int getSconto()
    {
        return this.sconto;
    }
    public void setSconto(int sconto)
    {
        this.sconto = sconto;
    }
}