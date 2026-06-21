package model.dao;

import model.Indirizzo;
import model.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IndirizzoDAO
{
	private static final String TABLE_NAME = "indirizzo";

	//Imposta "predefinito" = false a tutti gli indirizzi di un utente 
	public void resetPredefinitoPerUtente(int fkUtente, Connection conn) throws SQLException
	{
		PreparedStatement ps = null;
		String sql = "UPDATE " + TABLE_NAME + " SET predefinito = FALSE WHERE fk_utente = ?";
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fkUtente);
			ps.executeUpdate();
		}
		finally
		{
			if (ps != null) ps.close();
		}
	}
	
		// CAMBIA STATO PREDEFINITO
		public void cambiaStatoPredefinito(int idUtente, int idIndirizzo) throws SQLException
		{
			Connection conn = ConnectionPool.getConnection();
			PreparedStatement ps = null;

			String sql = "UPDATE " + TABLE_NAME + " SET predefinito = TRUE WHERE id_indirizzo = ?";

			try
			{
				resetPredefinitoPerUtente(idUtente, conn);

				ps = conn.prepareStatement(sql);
				ps.setInt(1, idIndirizzo);

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

	// INSERT
	public void doSave(Indirizzo i) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		// Se il nuovo indirizzo deve essere predefinito, azzeriamo gli altri dello stesso utente
		if (i.getPredefinito())
		{
			resetPredefinitoPerUtente(i.getFkUtente(), conn);
		}

		String sql =
				"INSERT INTO " + TABLE_NAME +
				" (via_numciv, paese, citta, provincia, codice_postale, predefinito, fk_utente) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?)";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString(1, i.getViaNumciv());
			ps.setString(2, i.getPaese());
			ps.setString(3, i.getCitta());
			ps.setString(4, i.getProvincia());
			ps.setString(5, i.getCodicePostale());
			ps.setBoolean(6, i.getPredefinito()); 
			ps.setInt(7, i.getFkUtente());

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

	// SELECT BY ID 
	public Indirizzo doRetrieveByKey(int idIndirizzo) throws SQLException
	{
		Indirizzo i = null;

		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_indirizzo = ?";

		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idIndirizzo);

			rs = ps.executeQuery();

			if(rs.next())
			{
				i = new Indirizzo();

				i.setIdIndirizzo(rs.getInt("id_indirizzo"));
				i.setViaNumciv(rs.getString("via_numciv"));
				i.setPaese(rs.getString("paese"));
				i.setCitta(rs.getString("citta"));
				i.setProvincia(rs.getString("provincia"));
				i.setCodicePostale(rs.getString("codice_postale"));
				i.setPredefinito(rs.getBoolean("predefinito"));
				i.setFkUtente(rs.getInt("fk_utente"));
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

		return i;
	}

	// SELECT BY UTENTE
	public List<Indirizzo> doRetrieveByUtente(int fkUtente) throws SQLException
	{
		List<Indirizzo> indirizzi = new ArrayList<>();

		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE fk_utente = ? ORDER BY predefinito DESC";

		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fkUtente);

			rs = ps.executeQuery();

			while(rs.next())
			{
				Indirizzo i = new Indirizzo();

				i.setIdIndirizzo(rs.getInt("id_indirizzo"));
				i.setViaNumciv(rs.getString("via_numciv"));
				i.setPaese(rs.getString("paese"));
				i.setCitta(rs.getString("citta"));
				i.setProvincia(rs.getString("provincia"));
				i.setCodicePostale(rs.getString("codice_postale"));
				i.setPredefinito(rs.getBoolean("predefinito")); 
				i.setFkUtente(rs.getInt("fk_utente"));

				indirizzi.add(i);
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

		return indirizzi;
	}

	// UPDATE 
	public void doUpdate(Indirizzo i) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		// Se l'aggiornamento imposta questo indirizzo come predefinito, azzeriamo gli altri
		if (i.getPredefinito()) {
			resetPredefinitoPerUtente(i.getFkUtente(), conn);
		}

		String sql =
				"UPDATE " + TABLE_NAME + " SET " +
				"via_numciv=?, paese=?, citta=?, provincia=?, codice_postale=?, predefinito=? " +
				"WHERE id_indirizzo=?";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString(1, i.getViaNumciv());
			ps.setString(2, i.getPaese());
			ps.setString(3, i.getCitta());
			ps.setString(4, i.getProvincia());
			ps.setString(5, i.getCodicePostale());
			ps.setBoolean(6, i.getPredefinito()); 
			ps.setInt(7, i.getIdIndirizzo());

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

	// DELETE
	public void doDelete(int idIndirizzo) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql = "DELETE FROM " + TABLE_NAME + " WHERE id_indirizzo = ?";

		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idIndirizzo);

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