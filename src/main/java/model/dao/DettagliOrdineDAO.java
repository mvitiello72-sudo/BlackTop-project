package model.dao;

import model.Prodotto;

import model.DettagliOrdine;
import model.connection.ConnectionPool;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DettagliOrdineDAO
{
	private static final String TABLE_NAME = "dettagliOrdine";

	//INSERT
	public void doSave(DettagliOrdine d) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql =
				"INSERT INTO " + TABLE_NAME +
				" (fk_ordine, fk_prodotto, quantita, prezzo_snapshot) " +
				"VALUES (?, ?, ?, ?)";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setInt(1, d.getFkOrdine());
			ps.setInt(2, d.getFkProdotto());
			ps.setInt(3, d.getQuantita());
			ps.setDouble(4, d.getPrezzoSnapshot());

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

	public List<DettagliOrdine> doRetrieveByOrdine(int fkOrdine) throws SQLException
	{
	    List<DettagliOrdine> dettagli = new ArrayList<>();

	    Connection conn = ConnectionPool.getConnection();
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String sql =
	            "SELECT d.fk_ordine, d.fk_prodotto, d.quantita, d.prezzo_snapshot, " +
	            "p.id_prodotto, p.nome, p.squadra, p.taglia, p.prezzo " +
	            "FROM dettagliOrdine d " +
	            "JOIN prodotto p ON d.fk_prodotto = p.id_prodotto " +
	            "WHERE d.fk_ordine = ?";

	    try
	    {
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, fkOrdine);

	        rs = ps.executeQuery();

	        while (rs.next())
	        {
	            DettagliOrdine d = new DettagliOrdine();

	            // dati riga ordine
	            d.setFkOrdine(rs.getInt("fk_ordine"));
	            d.setFkProdotto(rs.getInt("fk_prodotto"));
	            d.setQuantita(rs.getInt("quantita"));
	            d.setPrezzoSnapshot(rs.getDouble("prezzo_snapshot"));

	            // prodotto (JOIN)
	            Prodotto p = new Prodotto();
	            p.setIdProdotto(rs.getInt("id_prodotto"));
	            p.setNome(rs.getString("nome"));
	            p.setSquadra(rs.getString("squadra"));
	            p.setTaglia(rs.getString("taglia"));
	            p.setPrezzo(rs.getDouble("prezzo"));

	            d.setProdotto(p);

	            dettagli.add(d);
	        }
	    }
	    finally
	    {
	        try {
	            if (rs != null) rs.close();
	        } finally {
	            try {
	                if (ps != null) ps.close();
	            } finally {
	                ConnectionPool.releaseConnection(conn);
	            }
	        }
	    }

	    return dettagli;
	}
	//DELETE (opzionale, raro)
	public void doDelete(int fkOrdine, int fkProdotto) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql =
				"DELETE FROM " + TABLE_NAME +
				" WHERE fk_ordine = ? AND fk_prodotto = ?";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setInt(1, fkOrdine);
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

	//DELETE BY ORDINE (quando elimini ordine)
	public void doDeleteByOrdine(int fkOrdine) throws SQLException
	{
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement ps = null;

		String sql =
				"DELETE FROM " + TABLE_NAME +
				" WHERE fk_ordine = ?";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setInt(1, fkOrdine);

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