package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private String categoria;
    
    private List<Immagine> immagini = new ArrayList<>();

    // Costruttore vuoto 
    public Prodotto() { }

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

    public boolean getAttivo()
    {
        return this.attivo;
    }
    public void setAttivo(boolean attivo)
    {
        this.attivo = attivo;
    }
    
    public List<Immagine> getImmagini()  //restituisce la lista delle immagini 
    {
        return this.immagini;
    }

    public void setImmagini(List<Immagine> immagini) 
    {
        this.immagini = immagini;
    }
    
    public String getCategoria()
    {
        return this.categoria;
    }
    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }
}