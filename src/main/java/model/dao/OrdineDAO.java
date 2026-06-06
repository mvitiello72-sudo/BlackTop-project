package model.dao;

import model.Ordine;
import model.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO
{
	private static final String TABLE_NAME = "ordine";

	//INSERT
	public void doSave(Ordine o) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql =
				"INSERT INTO " + TABLE_NAME +
				" (data_ordine, stato, totale, fk_utente, fk_indirizzo) " +
				"VALUES (?, ?, ?, ?, ?)";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setDate(1, o.getDataOrdine());
			ps.setString(2, o.getStato());
			ps.setDouble(3, o.getTotale());
			ps.setInt(4, o.getFkUtente());
			ps.setInt(5, o.getFkIndirizzo());

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

		return o;
	}

	//SELECT ORDINI PER UTENTE
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

				ordini.add(o);
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

		return ordini;
	}

	//SELECT ALL (ADMIN)
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

				ordini.add(o);
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

		return ordini;
	}

	//UPDATE STATO ORDINE
	public void updateStato(int idOrdine, String stato) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql = "UPDATE " + TABLE_NAME + " SET stato=? WHERE id_ordine=?";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString(1, stato);
			ps.setInt(2, idOrdine);

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

	//DELETE (raro ma per admin)
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