package model.dao;

import model.Recensione;
import model.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAO
{
	private static final String TABLE_NAME = "recensione";

	//INSERT
	public void doSave(Recensione r) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql =
				"INSERT INTO " + TABLE_NAME +
				" (fk_utente, fk_prodotto, voto, commento, data_recensione) " +
				"VALUES (?, ?, ?, ?, ?)";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setInt(1, r.getFkUtente());
			ps.setInt(2, r.getFkProdotto());
			ps.setInt(3, r.getVoto());
			ps.setString(4, r.getCommento());
			ps.setDate(5, r.getDataRecensione());

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

	//SELECT BY PRODOTTO
	public List<Recensione> doRetrieveByProdotto(int fkProdotto) throws SQLException
	{
		List<Recensione> recensioni = new ArrayList<>();

		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql =
				"SELECT * FROM " + TABLE_NAME +
				" WHERE fk_prodotto = ? ORDER BY data_recensione DESC";

		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fkProdotto);

			rs = ps.executeQuery();

			while(rs.next())
			{
				Recensione r = new Recensione();

				r.setFkUtente(rs.getInt("fk_utente"));
				r.setFkProdotto(rs.getInt("fk_prodotto"));
				r.setVoto(rs.getInt("voto"));
				r.setCommento(rs.getString("commento"));
				r.setDataRecensione(rs.getDate("data_recensione"));

				recensioni.add(r);
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

		return recensioni;
	}

	//SELECT BY UTENTE + PRODOTTO (utile per evitare doppie recensioni)
	public Recensione doRetrieveByKey(int fkUtente, int fkProdotto) throws SQLException
	{
		Recensione r = null;

		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql =
				"SELECT * FROM " + TABLE_NAME +
				" WHERE fk_utente = ? AND fk_prodotto = ?";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setInt(1, fkUtente);
			ps.setInt(2, fkProdotto);

			rs = ps.executeQuery();

			if(rs.next())
			{
				r = new Recensione();

				r.setFkUtente(rs.getInt("fk_utente"));
				r.setFkProdotto(rs.getInt("fk_prodotto"));
				r.setVoto(rs.getInt("voto"));
				r.setCommento(rs.getString("commento"));
				r.setDataRecensione(rs.getDate("data_recensione"));
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

		return r;
	}

	//UPDATE
	public void doUpdate(Recensione r) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql =
				"UPDATE " + TABLE_NAME +
				" SET voto=?, commento=?, data_recensione=? " +
				"WHERE fk_utente=? AND fk_prodotto=?";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setInt(1, r.getVoto());
			ps.setString(2, r.getCommento());
			ps.setDate(3, r.getDataRecensione());
			ps.setInt(4, r.getFkUtente());
			ps.setInt(5, r.getFkProdotto());

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
	public void doDelete(int fkUtente, int fkProdotto) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql =
				"DELETE FROM " + TABLE_NAME +
				" WHERE fk_utente=? AND fk_prodotto=?";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setInt(1, fkUtente);
			ps.setInt(2, fkProdotto);

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