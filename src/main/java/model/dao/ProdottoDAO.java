package model.dao;

import model.Prodotto;
import model.Immagine;
import model.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProdottoDAO
{
	private static final String TABLE_NAME = "prodotto"; //nome della tabella nel DB
	
	//INSERT
	public void doSave(Prodotto p) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql =
				"INSERT INTO " + TABLE_NAME +
				" (nome, squadra, materiale, descrizione, prezzo, stock, taglia, attivo, sconto, categoria) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try
		{
			ps = conn.prepareStatement(sql);
		
			ps.setString(1, p.getNome());
			ps.setString(2, p.getSquadra());
			ps.setString(3, p.getMateriale());
			ps.setString(4, p.getDescrizione());
			ps.setDouble(5, p.getPrezzo());
			ps.setInt(6, p.getStock());
			ps.setString(7, p.getTaglia());
			ps.setBoolean(8, p.getAttivo());
			ps.setInt(9, p.getSconto());
			ps.setString(10, p.getCategoria());
        
			ps.executeUpdate();
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
			}
			finally
			{
				ConnectionPool.releaseConnection(conn);
			}
		}
	}
	
	//DELETE
	public void doDelete(int idProdotto) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		
		String sql = "DELETE FROM "+TABLE_NAME+" WHERE id_prodotto = ?";
		
		try
		{
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, idProdotto);
			
			ps.executeUpdate();
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
			}
			finally
			{
				ConnectionPool.releaseConnection(conn);
			}
		}
	}
	
	//UPDATE
	public void doUpdate(Prodotto p)throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		
		String sql = "UPDATE "+TABLE_NAME+" SET " +
                "nome=?, squadra=?, materiale=?, descrizione=?, prezzo=?, stock=?, taglia=?, attivo=?, sconto=?, categoria=? " +
                "WHERE id_prodotto=?";
		
		try
		{
			ps = conn.prepareStatement(sql);
		
			ps.setString(1, p.getNome());
			ps.setString(2, p.getSquadra());
			ps.setString(3, p.getMateriale());
			ps.setString(4, p.getDescrizione());
			ps.setDouble(5, p.getPrezzo());
			ps.setInt(6, p.getStock());
			ps.setString(7, p.getTaglia());
			ps.setBoolean(8, p.getAttivo());
			ps.setInt(9, p.getSconto());
			ps.setString(10, p.getCategoria());
			ps.setInt(11, p.getIdProdotto());
        
			ps.executeUpdate();
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
			}
			finally
			{
				ConnectionPool.releaseConnection(conn);
			}
		}
	}
	
	//SELECT (estrae anche le immagini)
    public Prodotto doRetrieveByKey(int idProdotto) throws SQLException
    {
    		Prodotto p = null;
    		Connection conn = ConnectionPool.getConnection();
    		PreparedStatement ps = null;
    		ResultSet rs = null;
    		
    		String sql = "SELECT p.*, i.id_immagine, i.percorso_immagine " +
    		             "FROM " + TABLE_NAME + " p " +
    		             "LEFT JOIN immagine i ON p.id_prodotto = i.fk_prodotto " +
    		             "WHERE p.id_prodotto = ?";
    		
    		try
    		{
    			ps = conn.prepareStatement(sql);
    			ps.setInt(1, idProdotto);
    			
    			rs = ps.executeQuery();
    			
    			while(rs.next()) //si usa il while per gestire le immagini del prodotto
    			{
    				if(p == null) 
    				{
    					p = new Prodotto();
    					p.setIdProdotto(rs.getInt("id_prodotto"));
        				p.setNome(rs.getString("nome"));
        				p.setSquadra(rs.getString("squadra"));
        				p.setMateriale(rs.getString("materiale"));
        				p.setDescrizione(rs.getString("descrizione"));
        				p.setPrezzo(rs.getDouble("prezzo"));
        				p.setStock(rs.getInt("stock"));
        				p.setTaglia(rs.getString("taglia"));
        				p.setAttivo(rs.getBoolean("attivo"));
        				p.setSconto(rs.getInt("sconto"));
        				p.setCategoria(rs.getString("categoria"));
    				}
    				
    				if(rs.getString("percorso_immagine") != null) 
    				{
    					Immagine img = new Immagine();
    					img.setIdImmagine(rs.getInt("id_immagine"));
    					img.setPercorsoImmagine(rs.getString("percorso_immagine"));
    					img.setFkProdotto(idProdotto);
    					
    					p.getImmagini().add(img); //get immagini restituisce la lista delle immagini
    				}
    			}
    		}
    		finally
    		{
    			try
    			{
    				if (rs != null)
    					rs.close();
    			}
    			finally
    			{
    				try
    				{
    					if (ps != null)
    						ps.close();
    				}
    				finally
    				{
    					ConnectionPool.releaseConnection(conn);
    				}
    			}
    		}

        return p;
    }
    
    //SELECT ALL
    public List<Prodotto> doRetrieveAll() throws SQLException
    {
        // Mappa per raggruppare i prodotti per ID ed evitare duplicati causati dal JOIN
        Map<Integer, Prodotto> mappaProdotti = new LinkedHashMap<>();
        
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Query con LEFT JOIN per estrarre ogni prodotto con tutte le sue immagini
        String sql = "SELECT p.*, i.id_immagine, i.percorso_immagine " +
                     "FROM " + TABLE_NAME + " p " +
                     "LEFT JOIN immagine i ON p.id_prodotto = i.fk_prodotto " +
                     "ORDER BY p.id_prodotto";
        
        try
        {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        
            while(rs.next())
            {
                // Legge l'ID del prodotto della riga corrente
                int idProd = rs.getInt("id_prodotto");
                
                // Cerca se il prodotto è già stato inserito nella mappa prima d'ora
                Prodotto p = mappaProdotti.get(idProd);
                
                // Se il prodotto NON è ancora nella mappa, lo crea e lo inserisce
                if(p == null) 
                {
                    p = new Prodotto();
                    p.setIdProdotto(idProd);
                    p.setNome(rs.getString("nome"));
                    p.setSquadra(rs.getString("squadra"));
                    p.setMateriale(rs.getString("materiale"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setStock(rs.getInt("stock"));
                    p.setTaglia(rs.getString("taglia"));
                    p.setAttivo(rs.getBoolean("attivo"));
                    p.setSconto(rs.getInt("sconto"));
                    p.setCategoria(rs.getString("categoria"));
                    
                    // Salva il nuovo prodotto nella mappa usando l'ID come chiave
                    mappaProdotti.put(idProd, p);
                }
                
                // Se la riga corrente contiene un'immagine valida (non null)
                if(rs.getString("percorso_immagine") != null) 
                {
                    Immagine img = new Immagine();
                    img.setIdImmagine(rs.getInt("id_immagine"));
                    img.setPercorsoImmagine(rs.getString("percorso_immagine"));
                    img.setFkProdotto(idProd);
                   
                    p.getImmagini().add(img); 
                }
            }
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
            }
            finally
            {
                try
                {
                    if (ps != null)
                        ps.close();
                }
                finally
                {
                    ConnectionPool.releaseConnection(conn);
                }
            }
        }

        // Estrae i prodotti rimasti nella mappa e li restituisce sotto forma di ArrayList
        return new ArrayList<>(mappaProdotti.values());
    }
    
 // SELECT BY FILTER
    public List<Prodotto> doRetrieveByFilter(String[] squadre, String categoria) throws SQLException
    {
        Map<Integer, Prodotto> mappaProdotti = new LinkedHashMap<>();
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        /*
         * Utilizziamo WHERE 1=1 come "ancora" logica.
         * Essendo 1=1 sempre vero, non altera il risultato, ma ci permette di concatenare 
         * tutte le condizioni successive (categorie o squadre) semplicemente usando "AND", 
         * evitando di dover gestire manualmente la distinzione tra WHERE e AND.
         */
        StringBuilder sql = new StringBuilder("SELECT p.*, i.id_immagine, i.percorso_immagine " +
                "FROM " + TABLE_NAME + " p " +
                "LEFT JOIN immagine i ON p.id_prodotto = i.fk_prodotto WHERE 1=1");

        // Aggiunta dinamica dei filtri
        if(categoria != null && !categoria.equals("tutte"))
            sql.append(" AND p.categoria = ?");
        
        if(squadre != null && squadre.length > 0)
        {
            sql.append(" AND p.squadra IN (");
            for(int i = 0; i < squadre.length; i++)
            {
            		if (i == 0)
            			sql.append("?");
            		else
            			sql.append(", ?");    
            }
            sql.append(")");
        }

        try
        {
            ps = conn.prepareStatement(sql.toString());
            
            // Setta i parametri dinamici
            int i = 1;
            if(categoria != null && !categoria.equals("tutte"))
            {
                ps.setString(i++, categoria);
            }
            
            if(squadre != null && squadre.length > 0)
            {
                for(String s : squadre)
                {
                    ps.setString(i++, s);
                }
            }

            rs = ps.executeQuery();
            
            while(rs.next()) {
                int idProd = rs.getInt("id_prodotto");
                Prodotto p = mappaProdotti.get(idProd);
                if(p == null) {
                    p = new Prodotto();
                    p.setIdProdotto(idProd);
                    p.setNome(rs.getString("nome"));
                    p.setSquadra(rs.getString("squadra"));
                    p.setMateriale(rs.getString("materiale"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setStock(rs.getInt("stock"));
                    p.setTaglia(rs.getString("taglia"));
                    p.setAttivo(rs.getBoolean("attivo"));
                    p.setSconto(rs.getInt("sconto"));
                    p.setCategoria(rs.getString("categoria"));
                    
                    mappaProdotti.put(idProd, p);
                }
                
                if(rs.getString("percorso_immagine") != null)
                {
                    Immagine img = new Immagine();
                    img.setIdImmagine(rs.getInt("id_immagine"));
                    img.setPercorsoImmagine(rs.getString("percorso_immagine"));
                    p.getImmagini().add(img);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(conn);
        }

        return new ArrayList<>(mappaProdotti.values());
    }
    
    //SELECT CORRELATI (Stessa squadra, escludendo il prodotto corrente)
 	public List<Prodotto> doRetrieveCorrelati(String squadra, int idProdotto) throws SQLException
 	{
 		Map<Integer, Prodotto> mappaProdotti = new LinkedHashMap<>();
 		Connection conn = ConnectionPool.getConnection();
 		PreparedStatement ps = null;
 		ResultSet rs = null;

 		
 		String sql = "SELECT p.*, i.id_immagine, i.percorso_immagine " +
 		             "FROM " + TABLE_NAME + " p " +
 		             "LEFT JOIN immagine i ON p.id_prodotto = i.fk_prodotto " +
 		             "WHERE p.squadra = ? AND p.id_prodotto != ? AND p.attivo = true " +
 		             "LIMIT 10"; //prende massimo 10 prodotti

 		try
 		{
 			ps = conn.prepareStatement(sql);
 			ps.setString(1, squadra);
 			ps.setInt(2, idProdotto);

 			rs = ps.executeQuery();

 			while (rs.next())
 			{
 				int idProd = rs.getInt("id_prodotto");
 				Prodotto p = mappaProdotti.get(idProd);

 				if (p == null)
 				{
 					p = new Prodotto();
 					p.setIdProdotto(idProd);
 					p.setNome(rs.getString("nome"));
 					p.setSquadra(rs.getString("squadra"));
 					p.setMateriale(rs.getString("materiale"));
 					p.setDescrizione(rs.getString("descrizione"));
 					p.setPrezzo(rs.getDouble("prezzo"));
 					p.setStock(rs.getInt("stock"));
 					p.setTaglia(rs.getString("taglia"));
 					p.setAttivo(rs.getBoolean("attivo"));
 					p.setSconto(rs.getInt("sconto"));
 					p.setCategoria(rs.getString("categoria"));

 					mappaProdotti.put(idProd, p);
 				}

 				if (rs.getString("percorso_immagine") != null)
 				{
 					Immagine img = new Immagine();
 					img.setIdImmagine(rs.getInt("id_immagine"));
 					img.setPercorsoImmagine(rs.getString("percorso_immagine"));
 					img.setFkProdotto(idProd);

 					p.getImmagini().add(img);
 				}
 			}
 		}
 		finally
 		{
 			try
 			{
 				if (rs != null)
 					rs.close();
 			}
 			finally
 			{
 				try
 				{
 					if (ps != null)
 						ps.close();
 				}
 				finally
 				{
 					ConnectionPool.releaseConnection(conn);
 				}
 			}
 		}
 		return new ArrayList<>(mappaProdotti.values());
 	}
 	
 	// COUNT PRODOTTI ATTIVI
 	public int countProdottiAttivi() throws SQLException
 	{
 		Connection conn = ConnectionPool.getConnection();
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		int totale = 0;
 		
 		String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE attivo = true";
 		
 		try
 		{
 			ps = conn.prepareStatement(sql);
 			rs = ps.executeQuery();
 			
 			if (rs.next())
 			{
 				totale = rs.getInt(1); //Estrae il risultato della prima colonna (il COUNT)
 			}
 		}
 		finally
 		{
 			try
 			{
 				if (rs != null)
 					rs.close();
 			}
 			finally
 			{
 				try
 				{
 					if (ps != null)
 						ps.close();
 				}
 				finally
 				{
 					ConnectionPool.releaseConnection(conn);
 				}
 			}
 		}
 		
 		return totale;
 	}
 	
 	// UPDATE STATO ATTIVO 
 	public void updateAttivo(int idProdotto, boolean nuovoStato) throws SQLException
 	{
 		Connection conn = ConnectionPool.getConnection();
 		PreparedStatement ps = null;
 		
 		String sql = "UPDATE " + TABLE_NAME + " SET attivo = ? WHERE id_prodotto = ?";
 		
 		try
 		{
 			ps = conn.prepareStatement(sql);
 			
 			ps.setBoolean(1, nuovoStato);
 			ps.setInt(2, idProdotto);
 			
 			ps.executeUpdate();
 		}
 		finally
 		{
 			try
 			{
 				if (ps != null)
 					ps.close();
 			}
 			finally
 			{
 				ConnectionPool.releaseConnection(conn);
 			}
 		}
 	}
}