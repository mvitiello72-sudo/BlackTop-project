package model.dao;

import model.Utente;
import model.utils.PasswordUtils;
import model.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO
{
	private static final String TABLE_NAME = "utente";

	//INSERT
		public void doSave(Utente u) throws SQLException
		{
			Connection conn = ConnectionPool.getConnection();
			PreparedStatement ps = null;

			String sql =
					"INSERT INTO " + TABLE_NAME +
					" (email, nome, cognome, password, ruolo, cellulare) " +
					"VALUES (?, ?, ?, ?, ?, ?)";

			try
			{
				ps = conn.prepareStatement(sql);

				ps.setString(1, u.getEmail());
				ps.setString(2, u.getNome());
				ps.setString(3, u.getCognome());
				
				// Cifriamo la password in SHA-512 prima di salvarla sul DB
				String passwordCifrata = PasswordUtils.toDigest(u.getPassword());
				ps.setString(4, passwordCifrata);
				
				ps.setString(5, u.getRuolo());
				ps.setString(6, u.getCellulare());

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
		public void doUpdate(Utente u) throws SQLException
		{
			Connection conn = ConnectionPool.getConnection();
			PreparedStatement ps = null;

			String sql =
					"UPDATE " + TABLE_NAME +
					" SET email=?, nome=?, cognome=?, password=?, ruolo=?, cellulare=? " +
					"WHERE id_utente=?";

			try
			{
				ps = conn.prepareStatement(sql);

				ps.setString(1, u.getEmail());
				ps.setString(2, u.getNome());
				ps.setString(3, u.getCognome());
				
				// Cifriamo la password prima di aggiornare il record sul DB
				String passwordCifrata = PasswordUtils.toDigest(u.getPassword());
				ps.setString(4, passwordCifrata);
				
				ps.setString(5, u.getRuolo());
				ps.setString(6, u.getCellulare());
				ps.setInt(7, u.getIdUtente());

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
	public void doDelete(int id) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql = "DELETE FROM " + TABLE_NAME + " WHERE id_utente = ?";

		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

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

	//SELECT BY EMAIL (LOGIN)
	public Utente doRetrieveByEmail(String email) throws SQLException
	{
		Utente u = null;

		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

		try
		{
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);

			rs = ps.executeQuery();

			if(rs.next())
			{
				u = new Utente();

				u.setIdUtente(rs.getInt("id_utente"));
				u.setEmail(rs.getString("email"));
				u.setNome(rs.getString("nome"));
				u.setCognome(rs.getString("cognome"));
				u.setPassword(rs.getString("password"));
				u.setRuolo(rs.getString("ruolo"));
				u.setCellulare(rs.getString("cellulare"));
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

		return u;
	}

	//SELECT BY ID
	public Utente doRetrieveById(int id) throws SQLException
	{
		Utente u = null;

		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_utente = ?";

		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if(rs.next())
			{
				u = new Utente();

				u.setIdUtente(rs.getInt("id_utente"));
				u.setEmail(rs.getString("email"));
				u.setNome(rs.getString("nome"));
				u.setCognome(rs.getString("cognome"));
				u.setPassword(rs.getString("password"));
				u.setRuolo(rs.getString("ruolo"));
				u.setCellulare(rs.getString("cellulare"));
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

		return u;
	}

	//SELECT ALL (ADMIN)
	public List<Utente> doRetrieveAll() throws SQLException
	{
		List<Utente> utenti = new ArrayList<>();

		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM " + TABLE_NAME;

		try
		{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next())
			{
				Utente u = new Utente();

				u.setIdUtente(rs.getInt("id_utente"));
				u.setEmail(rs.getString("email"));
				u.setNome(rs.getString("nome"));
				u.setCognome(rs.getString("cognome"));
				u.setPassword(rs.getString("password"));
				u.setRuolo(rs.getString("ruolo"));
				u.setCellulare(rs.getString("cellulare"));

				utenti.add(u);
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

		return utenti;
	}
	
	// COUNT UTENTI REGISTRATI
	public int countUtentiRegistrati() throws SQLException
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
		
			if (rs.next())
			{
				totale = rs.getInt(1); // Prende il primo valore, ovvero il count
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
	
	public boolean updateRuolo(int id, String nuovo) throws SQLException
	{
	    Connection conn = ConnectionPool.getConnection();
	    PreparedStatement ps = null;
	    int righeModificate = 0;

	    String sql = "UPDATE utente SET ruolo = ? WHERE id_utente = ?";

	    try 
	    {	        
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, nuovo);
	        ps.setInt(2, id);
	        righeModificate = ps.executeUpdate();
	    } 
	    finally 
	    {
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        if (conn != null) {
	            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }

	    // Ritorna true se almeno una riga è stata modificata nel DB, false altrimenti
	    return righeModificate > 0;
	}
	
}