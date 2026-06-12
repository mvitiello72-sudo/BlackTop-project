package model.dao;

import model.Utente;
import model.Ordine;
import model.Carrello;
import model.ElementoCarrello;
import model.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO
{
	private static final String TABLE_NAME = "ordine";

	public int salvaOrdineCompleto(Ordine ordine, Carrello carrello) throws SQLException 
	{
	    Connection conn = null;
	    PreparedStatement psOrdine = null;
	    PreparedStatement psDettaglio = null;
	    PreparedStatement psUpdateStock = null; 
	    ResultSet generatedKeys = null;
	    int idOrdine = -1;

	    String sqlOrdine = "INSERT INTO " + TABLE_NAME + 
	            " (data_ordine, stato, totale, fk_utente, fk_indirizzo, fk_pagamento) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    String sqlDettaglio = "INSERT INTO dettagliOrdine (fk_ordine, fk_prodotto, quantita, prezzo_snapshot) VALUES (?, ?, ?, ?)";
	    
	    // SQL MODIFICATO: Scala lo stock E se lo stock diventa <= 0 imposta attivo = 0 (false)
	    String sqlUpdateStock = "UPDATE prodotto " +
	                            "SET stock = stock - ?, " +
	                            "    attivo = IF(stock <= 0, 0, attivo) " +
	                            "WHERE id_prodotto = ?";

	    try 
	    {
	        conn = ConnectionPool.getConnection();
	        conn.setAutoCommit(false); // Inizia la transazione atomica

	        // 1. Inseriamo la testa dell'ordine
	        psOrdine = conn.prepareStatement(sqlOrdine, Statement.RETURN_GENERATED_KEYS);
	        psOrdine.setDate(1, ordine.getDataOrdine());
	        psOrdine.setString(2, ordine.getStato());
	        psOrdine.setDouble(3, ordine.getTotale());
	        psOrdine.setInt(4, ordine.getFkUtente());
	        psOrdine.setInt(5, ordine.getFkIndirizzo());
	        psOrdine.setInt(6, ordine.getFkPagamento()); 
	        
	        psOrdine.executeUpdate();

	        // Recuperiamo l'id_ordine autogenerato
	        generatedKeys = psOrdine.getGeneratedKeys();
	        if (generatedKeys.next()) 
	        {
	            idOrdine = generatedKeys.getInt(1);
	        }

	        // Prepariamo i due statement per i dettagli e per lo stock
	        psDettaglio = conn.prepareStatement(sqlDettaglio);
	        psUpdateStock = conn.prepareStatement(sqlUpdateStock);

	        // 2. Cicliamo il carrello
	        for(ElementoCarrello item : carrello.getElementi()) 
	        {
	            // Batch dettagli ordine
	            psDettaglio.setInt(1, idOrdine);
	            psDettaglio.setInt(2, item.getProdotto().getIdProdotto());
	            psDettaglio.setInt(3, item.getQuantita());
	            psDettaglio.setDouble(4, item.getProdotto().getPrezzo()); 
	            psDettaglio.addBatch(); 

	            // Batch aggiornamento stock e stato attivo
	            psUpdateStock.setInt(1, item.getQuantita()); 
	            psUpdateStock.setInt(2, item.getProdotto().getIdProdotto()); 
	            psUpdateStock.addBatch();
	        }
	        
	        // Eseguiamo i batch
	        psDettaglio.executeBatch(); 
	        psUpdateStock.executeBatch(); 

	        conn.commit(); // Conferma la transazione completa
	    } 
	    catch (SQLException e) 
	    {
	        if (conn != null) conn.rollback(); // Annulla tutto in caso di errore
	        throw e;
	    } 
	    finally 
	    {
	        try { if (generatedKeys != null) generatedKeys.close(); } catch (Exception e) {}
	        try { if (psOrdine != null) psOrdine.close(); } catch (Exception e) {}
	        try { if (psDettaglio != null) psDettaglio.close(); } catch (Exception e) {}
	        try { if (psUpdateStock != null) psUpdateStock.close(); } catch (Exception e) {} 
	        
	        try { 
	            if (conn != null) {
	                conn.setAutoCommit(true); 
	            }
	        } catch (Exception e) {}
	        
	        ConnectionPool.releaseConnection(conn);
	    }
	    return idOrdine;
	}

	//INSERT
	public void doSave(Ordine o) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql =
				"INSERT INTO " + TABLE_NAME +
				" (data_ordine, stato, totale, fk_utente, fk_indirizzo, fk_pagamento) " +
				"VALUES (?, ?, ?, ?, ?, ?)";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setDate(1, o.getDataOrdine());
			ps.setString(2, o.getStato());
			ps.setDouble(3, o.getTotale());
			ps.setInt(4, o.getFkUtente());
			ps.setInt(5, o.getFkIndirizzo());
			ps.setInt(6, o.getFkPagamento());

			ps.executeUpdate();
		}
		finally
		{
			try { if (ps != null) ps.close(); } finally { ConnectionPool.releaseConnection(conn); }
		}
	}
	
	//INSERT CON RITORNO ID 
	public int doSaveAndReturnId(Ordine o) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql =
				"INSERT INTO " + TABLE_NAME +
				" (data_ordine, stato, totale, fk_utente, fk_indirizzo, fk_pagamento) " +
				"VALUES (?, ?, ?, ?, ?, ?)";

		try
		{
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setDate(1, o.getDataOrdine());
			ps.setString(2, o.getStato());
			ps.setDouble(3, o.getTotale());
			ps.setInt(4, o.getFkUtente());
			ps.setInt(5, o.getFkIndirizzo());
			ps.setInt(6, o.getFkPagamento());

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();

			if (rs.next())
			{
				return rs.getInt(1);
			}

			return -1;
		}
		finally
		{
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (ps != null) ps.close(); } catch (Exception e) {}
			ConnectionPool.releaseConnection(conn);
		}
	}

	//SELECT BY ID 
	public Ordine doRetrieveByKey(int idOrdine) throws SQLException
	{
		Ordine o = null;
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_ordine = ?";

		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idOrdine);
			rs = ps.executeQuery();

			if(rs.next())
			{
				o = new Ordine();
				o.setIdOrdine(rs.getInt("id_ordine"));
				o.setDataOrdine(rs.getDate("data_ordine"));
				o.setStato(rs.getString("stato"));
				o.setTotale(rs.getDouble("totale"));
				o.setFkUtente(rs.getInt("fk_utente"));
				o.setFkIndirizzo(rs.getInt("fk_indirizzo"));
				o.setFkPagamento(rs.getInt("fk_pagamento"));
			}
		}
		finally
		{
			try { if (rs != null) rs.close(); } finally { try { if (ps != null) ps.close(); } finally { ConnectionPool.releaseConnection(conn); } }
		}
		return o;
	}

	//SELECT PER UTENTE 
	public List<Ordine> doRetrieveByUtente(int fkUtente) throws SQLException
	{
		List<Ordine> ordini = new ArrayList<>();
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE fk_utente = ? ORDER BY data_ordine DESC";

		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fkUtente);
			rs = ps.executeQuery();

			while(rs.next())
			{
				Ordine o = new Ordine();
				o.setIdOrdine(rs.getInt("id_ordine"));
				o.setDataOrdine(rs.getDate("data_ordine"));
				o.setStato(rs.getString("stato"));
				o.setTotale(rs.getDouble("totale"));
				o.setFkUtente(rs.getInt("fk_utente"));
				o.setFkIndirizzo(rs.getInt("fk_indirizzo"));
				o.setFkPagamento(rs.getInt("fk_pagamento")); 

				ordini.add(o);
			}
		}
		finally
		{
			try { if (rs != null) rs.close(); } finally { try { if (ps != null) ps.close(); } finally { ConnectionPool.releaseConnection(conn); } }
		}
		return ordini;
	}

	//SELECT ALL ADMIN
	public List<Ordine> doRetrieveAll() throws SQLException
	{
		List<Ordine> ordini = new ArrayList<>();
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY data_ordine DESC";

		try
		{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next())
			{
				Ordine o = new Ordine();
				o.setIdOrdine(rs.getInt("id_ordine"));
				o.setDataOrdine(rs.getDate("data_ordine"));
				o.setStato(rs.getString("stato"));
				o.setTotale(rs.getDouble("totale"));
				o.setFkUtente(rs.getInt("fk_utente"));
				o.setFkIndirizzo(rs.getInt("fk_indirizzo"));
				o.setFkPagamento(rs.getInt("fk_pagamento"));

				ordini.add(o);
			}
		}
		finally
		{
			try { if (rs != null) rs.close(); } finally { try { if (ps != null) ps.close(); } finally { ConnectionPool.releaseConnection(conn); } }
		}
		return ordini;
	}

	// UPDATE STATO ORDINE
	public boolean updateStato(int idOrdine, String stato) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		int righeModificate = 0;
		
		String sql = "UPDATE " + TABLE_NAME + " SET stato=? WHERE id_ordine=?";
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setString(1, stato);
			ps.setInt(2, idOrdine);
			
			righeModificate = ps.executeUpdate();
		}
		finally
		{
			try { if (ps != null) ps.close(); } finally { ConnectionPool.releaseConnection(conn); }
		}
		
		return righeModificate > 0;
	}

	// DELETE
	public void doDelete(int idOrdine) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE id_ordine=?";
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idOrdine);
			ps.executeUpdate();
		}
		finally
		{
			try { if (ps != null) ps.close(); } finally { ConnectionPool.releaseConnection(conn); }
		}
	}
	
	// COUNT ORDINI TOTALI
	public int countOrdiniTotali() throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totale = 0;
		String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
		try
		{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) { totale = rs.getInt(1); }
		}
		finally
		{
			try { if (rs != null) rs.close(); } finally { try { if (ps != null) ps.close(); } finally { ConnectionPool.releaseConnection(conn); } }
		}
		return totale;
	}
	
	//SELECT CON UTENTE
	public List<Ordine> doRetrieveAllConUtenti() throws SQLException
	{
		List<Ordine> ordini = new ArrayList<>();
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT o.*, u.nome, u.cognome, u.email " +
				     "FROM " + TABLE_NAME + " o " +
				     "JOIN utente u ON o.fk_utente = u.id_utente " +
				     "ORDER BY o.data_ordine DESC";
		try
		{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				Ordine o = new Ordine();
				o.setIdOrdine(rs.getInt("id_ordine"));
				o.setDataOrdine(rs.getDate("data_ordine"));
				o.setStato(rs.getString("stato"));
				o.setTotale(rs.getDouble("totale"));
				o.setFkUtente(rs.getInt("fk_utente"));
				o.setFkIndirizzo(rs.getInt("fk_indirizzo"));
				o.setFkPagamento(rs.getInt("fk_pagamento"));
				
				Utente u = new Utente();
				u.setIdUtente(rs.getInt("fk_utente"));
				u.setNome(rs.getString("nome"));
				u.setCognome(rs.getString("cognome"));
				u.setEmail(rs.getString("email"));

				o.setUtente(u);
				ordini.add(o);
			}
		}
		finally
		{
			try { if (rs != null) rs.close(); } finally { try { if (ps != null) ps.close(); } finally { ConnectionPool.releaseConnection(conn); } }
		}
		return ordini;
	}

	//SELECT CON FILTRI 
	public List<Ordine> doRetrieveByFilters(String dataInizio, String dataFine, String cliente) throws SQLException
	{
		List<Ordine> ordini = new ArrayList<>();
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuilder sql = new StringBuilder(
			"SELECT o.*, u.nome, u.cognome, u.email " +
			"FROM " + TABLE_NAME + " o " +
			"JOIN utente u ON o.fk_utente = u.id_utente " +
			"WHERE 1=1"
		);

		if (dataInizio != null && !dataInizio.isEmpty()) { sql.append(" AND o.data_ordine >= ?"); }
		if (dataFine != null && !dataFine.isEmpty()) { sql.append(" AND o.data_ordine <= ?"); }
		if (cliente != null && !cliente.isEmpty()) { sql.append(" AND u.email LIKE ?"); }

		sql.append(" ORDER BY o.data_ordine DESC");

		try
		{
			ps = conn.prepareStatement(sql.toString());
			int i = 1;

			if (dataInizio != null && !dataInizio.isEmpty()) { ps.setDate(i++, java.sql.Date.valueOf(dataInizio)); }
			if (dataFine != null && !dataFine.isEmpty()) { ps.setDate(i++, java.sql.Date.valueOf(dataFine)); }
			if (cliente != null && !cliente.isEmpty()) { ps.setString(i++, "%" + cliente + "%"); }

			rs = ps.executeQuery();

			while (rs.next())
			{
				Ordine o = new Ordine();
				o.setIdOrdine(rs.getInt("id_ordine"));
				o.setDataOrdine(rs.getDate("data_ordine"));
				o.setStato(rs.getString("stato"));
				o.setTotale(rs.getDouble("totale"));
				o.setFkUtente(rs.getInt("fk_utente"));
				o.setFkIndirizzo(rs.getInt("fk_indirizzo"));
				o.setFkPagamento(rs.getInt("fk_pagamento"));

				Utente u = new Utente();
				u.setIdUtente(rs.getInt("fk_utente"));
				u.setNome(rs.getString("nome"));
				u.setCognome(rs.getString("cognome"));
				u.setEmail(rs.getString("email"));

				o.setUtente(u);
				ordini.add(o);
			}
		}
		finally
		{
			try { if (rs != null) rs.close(); } finally { try { if (ps != null) ps.close(); } finally { ConnectionPool.releaseConnection(conn); } }
		}
		return ordini;
	}
}