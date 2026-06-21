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
	
	public void doSave(Prodotto p) throws SQLException {
	    Connection conn = ConnectionPool.getConnection();
	    PreparedStatement ps = null;
	    
	    String sql = "INSERT INTO " + TABLE_NAME + 
	                 " (nome, squadra, materiale, descrizione, prezzo, stock, taglia, attivo, categoria) " + 
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    try {
	        ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
	        
	        ps.setString(1, p.getNome());
	        ps.setString(2, p.getSquadra());
	        ps.setString(3, p.getMateriale());
	        ps.setString(4, p.getDescrizione());
	        ps.setDouble(5, p.getPrezzo());
	        ps.setInt(6, p.getStock());
	        ps.setString(7, p.getTaglia());
	        ps.setBoolean(8, p.getAttivo());
	        ps.setString(9, p.getCategoria());
	        
	        ps.executeUpdate();
	        
	        try (java.sql.ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                p.setIdProdotto(generatedKeys.getInt(1));
	            }
	        }
	    } finally {
	        if (ps != null) ps.close();
	        ConnectionPool.releaseConnection(conn);
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
                "nome=?, squadra=?, materiale=?, descrizione=?, prezzo=?, stock=?, taglia=?, attivo=?, categoria=? " +
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
			ps.setString(9, p.getCategoria());
			ps.setInt(10, p.getIdProdotto());
        
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
    			
    			while(rs.next())
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
        				p.setCategoria(rs.getString("categoria"));
    				}
    				
    				if(rs.getString("percorso_immagine") != null) 
    				{
    					Immagine img = new Immagine();
    					img.setIdImmagine(rs.getInt("id_immagine"));
    					img.setPercorsoImmagine(rs.getString("percorso_immagine"));
    					img.setFkProdotto(idProdotto);
    					
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

        return p;
    }
    
    //SELECT ALL
    public List<Prodotto> doRetrieveAll() throws SQLException
    {
    		//serve per gestire anche le immaigni, se si usa una list, avremo 
    		//lo stesso prodotto ripetuto tante volte quante sono le immagini
        Map<Integer, Prodotto> mappaProdotti = new LinkedHashMap<>();
        
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
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
                int idProd = rs.getInt("id_prodotto");
                Prodotto p = mappaProdotti.get(idProd);
                
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
                    p.setCategoria(rs.getString("categoria"));
                    
                    mappaProdotti.put(idProd, p);
                }
                
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

        return new ArrayList<>(mappaProdotti.values());
    }
    
    // SELECT ALL ATTIVI
    public List<Prodotto> doRetrieveAllAttivi() throws SQLException
    {
        Map<Integer, Prodotto> mappaProdotti = new LinkedHashMap<>();
        
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT p.*, i.id_immagine, i.percorso_immagine " +
                     "FROM " + TABLE_NAME + " p " +
                     "LEFT JOIN immagine i ON p.id_prodotto = i.fk_prodotto " +
                     "WHERE p.attivo = true " +
                     "ORDER BY p.id_prodotto";
        
        try
        {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        
            while(rs.next())
            {
                int idProd = rs.getInt("id_prodotto");
                Prodotto p = mappaProdotti.get(idProd);
                
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
                    p.setCategoria(rs.getString("categoria"));
                    
                    mappaProdotti.put(idProd, p);
                }
                
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
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(conn);
        }

        return new ArrayList<>(mappaProdotti.values());
    }
    
    // SELECT ALL ATTIVI UNIVOCI
    public List<Prodotto> doRetrieveAllAttiviUnivoci() throws SQLException
    {
        Map<Integer, Prodotto> mappaProdotti = new LinkedHashMap<>();
        
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //evita i duplicati, prende solo quello con id più basso
        String sql = "SELECT p.*, i.id_immagine, i.percorso_immagine " +
                     "FROM " + TABLE_NAME + " p " +
                     "LEFT JOIN immagine i ON p.id_prodotto = i.fk_prodotto " +
                     "WHERE p.id_prodotto IN (" +
                     "    SELECT MIN(p2.id_prodotto) " +
                     "    FROM " + TABLE_NAME + " p2 " +
                     "    WHERE p2.attivo = true " +
                     "    GROUP BY p2.nome, p2.squadra" +
                     ") " +
                     "ORDER BY p.id_prodotto";
        
        try
        {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        
            while(rs.next())
            {
                int idProd = rs.getInt("id_prodotto");
                Prodotto p = mappaProdotti.get(idProd);
                
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
                    p.setCategoria(rs.getString("categoria"));
                    
                    mappaProdotti.put(idProd, p);
                }
                
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
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(conn);
        }

        return new ArrayList<>(mappaProdotti.values());
    }
 
    // SELECT BY FILTER
    public List<Prodotto> doRetrieveByFilter(String[] squadre, String categoria) throws SQLException
    {
        Map<Integer, Prodotto> mappaProdotti = new LinkedHashMap<>();
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder("SELECT p.*, i.id_immagine, i.percorso_immagine " +
                "FROM " + TABLE_NAME + " p " +
                "LEFT JOIN immagine i ON p.id_prodotto = i.fk_prodotto " +
                "WHERE p.id_prodotto IN (" +
                "    SELECT MIN(p2.id_prodotto) " +
                "    FROM " + TABLE_NAME + " p2 " +
                "    WHERE p2.attivo = true ");

        if(categoria != null && !categoria.equals("tutte")) {
            sql.append("AND p2.categoria = ? ");
        }

        if(squadre != null && squadre.length > 0) {
            sql.append("AND p2.squadra IN (");
            for(int i = 0; i < squadre.length; i++) {
                if (i == 0) sql.append("?");
                else sql.append(", ?");    
            }
            sql.append(") ");
        }

        sql.append("    GROUP BY p2.nome, p2.squadra ");
        sql.append(") ORDER BY p.id_prodotto");

        try
        {
            ps = conn.prepareStatement(sql.toString());
            
            int paramIndex = 1;
            if(categoria != null && !categoria.equals("tutte")) {
                ps.setString(paramIndex++, categoria);
            }
            
            if(squadre != null && squadre.length > 0) {
                for(String s : squadre) {
                    ps.setString(paramIndex++, s);
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
                    p.setCategoria(rs.getString("categoria"));
                    
                    mappaProdotti.put(idProd, p);
                }
                
                if(rs.getString("percorso_immagine") != null) {
                    Immagine img = new Immagine();
                    img.setIdImmagine(rs.getInt("id_immagine"));
                    img.setPercorsoImmagine(rs.getString("percorso_immagine"));
                    img.setFkProdotto(idProd);
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
    
    // SELECT CORRELATI
    public List<Prodotto> doRetrieveCorrelati(String squadres, String nomeProdottoCorrente) throws SQLException
    {
        Map<Integer, Prodotto> mappaProdotti = new LinkedHashMap<>();
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT p.*, i.id_immagine, i.percorso_immagine " +
                     "FROM " + TABLE_NAME + " p " +
                     "LEFT JOIN immagine i ON p.id_prodotto = i.fk_prodotto " +
                     "WHERE p.squadra = ? AND p.attivo = true " +
                     "AND p.id_prodotto IN (" +
                     "    SELECT MIN(p2.id_prodotto) " +
                     "    FROM " + TABLE_NAME + " p2 " +
                     "    WHERE p2.nome != ? " +
                     "    GROUP BY p2.nome" +
                     ") " +
                     "LIMIT 10";

        try
        {
            ps = conn.prepareStatement(sql);
            ps.setString(1, squadres);
            ps.setString(2, nomeProdottoCorrente);

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
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConnectionPool.releaseConnection(conn);
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
				totale = rs.getInt(1);
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
	
	// BY NOME UNIVOCI
	public List<Prodotto> doRetrieveByNomeUnivoci(String ricerca) throws SQLException 
	{
		Map<Integer, Prodotto> mappaProdotti = new LinkedHashMap<>();
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT p.*, i.id_immagine, i.percorso_immagine " +
		             "FROM " + TABLE_NAME + " p " +
		             "LEFT JOIN immagine i ON p.id_prodotto = i.fk_prodotto " +
		             "WHERE p.id_prodotto IN (" +
		             "    SELECT MIN(p2.id_prodotto) " +
		             "    FROM " + TABLE_NAME + " p2 " +
		             "    WHERE p2.attivo = true AND (p2.nome LIKE ? OR p2.squadra LIKE ?) " +
		             "    GROUP BY p2.nome, p2.squadra" +
		             ") " +
		             "ORDER BY p.id_prodotto";

		try 
		{
			ps = conn.prepareStatement(sql);
			String param = "%" + ricerca + "%";
			ps.setString(1, param);
			ps.setString(2, param);
			
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
			try { if (rs != null) rs.close(); } 
			finally { try { if (ps != null) ps.close(); } 
			finally { ConnectionPool.releaseConnection(conn); } }
		}
		
		return new ArrayList<>(mappaProdotti.values());
	}
	
	// BY NOME (Standard)
	public List<Prodotto> doRetrieveByNome(String ricerca) throws SQLException {
	    List<Prodotto> prodotti = new ArrayList<>();
	    Connection conn = ConnectionPool.getConnection();
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String sql = "SELECT * FROM prodotto WHERE attivo = 1 AND (nome LIKE ? OR squadra LIKE ?) LIMIT 5";

	    try
	    {
	        ps = conn.prepareStatement(sql);
	        String param = "%" + ricerca + "%";
	        ps.setString(1, param);
	        ps.setString(2, param);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Prodotto p = new Prodotto();
	            p.setIdProdotto(rs.getInt("id_prodotto"));
	            p.setNome(rs.getString("nome"));
	            p.setSquadra(rs.getString("squadra"));
	            p.setMateriale(rs.getString("materiale"));
	            p.setDescrizione(rs.getString("descrizione"));
	            p.setPrezzo(rs.getDouble("prezzo"));
	            p.setStock(rs.getInt("stock"));
	            p.setTaglia(rs.getString("taglia"));
	            p.setAttivo(rs.getBoolean("attivo"));
	            p.setCategoria(rs.getString("categoria"));
	            prodotti.add(p);
	        }
	    } finally {
	        try { if (rs != null) rs.close(); } finally { try { if (ps != null) ps.close(); } finally { ConnectionPool.releaseConnection(conn); } }
	    }
	    return prodotti;
	}
}