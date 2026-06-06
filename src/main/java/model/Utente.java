package model;

import java.io.Serializable;

public class Utente implements Serializable
{
	private static final long serialVersionUID = 1L;

    private int idUtente;
    private String email;
    private String nome;
    private String cognome;
    private String password;
    private String ruolo; //USER o ADMIN
    private String cellulare;

    // Costruttore vuoto
    public Utente() { }

    public Utente(int idUtente, String email, String nome, String cognome, String password, String ruolo, 
    			  String cellulare)
    {
        this.idUtente = idUtente;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.ruolo = ruolo;
        this.cellulare = cellulare;
    }

    // Getter e Setter
    public int getIdUtente()
    {
        return this.idUtente;
    }
    public void setIdUtente(int idUtente)
    {
        this.idUtente = idUtente;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getNome()
    {
        return this.nome;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getCognome()
    {
        return this.cognome;
    }
    public void setCognome(String cognome)
    {
        this.cognome = cognome;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRuolo()
    {
        return this.ruolo;
    }
    public void setRuolo(String ruolo)
    {
        this.ruolo = ruolo;
    }

    public String getCellulare()
    {
        return this.cellulare;
    }
    public void setCellulare(String cellulare)
    {
        this.cellulare = cellulare;
    }
}