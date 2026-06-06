package model.dao;

import model.Prodotto;
import model.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
				" (nome, squadra, materiale, descrizione, prezzo, stock, taglia, attivo, sconto) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
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
			ps.setBoolean(8, p.isAttivo());
			ps.setInt(9, p.getSconto());
        
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
                "nome=?, squadra=?, materiale=?, descrizione=?, prezzo=?, stock=?, taglia=?, attivo=?, sconto=? " +
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
			ps.setBoolean(8, p.isAttivo());
			ps.setInt(9, p.getSconto());
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
	
	//SELECT
    public Prodotto doRetrieveByKey(int idProdotto) throws SQLException
    {
    		Prodotto p = null;
    		Connection conn = ConnectionPool.getConnection();
    		PreparedStatement ps = null;
    		ResultSet rs = null;
    		
    		String sql = "SELECT * FROM "+TABLE_NAME+" WHERE id_prodotto = ?";
    		
    		try
    		{
    			ps = conn.prepareStatement(sql);
    			
    			ps.setInt(1, idProdotto);
    			
    			rs = ps.executeQuery();
    			
    			if(rs.next())
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
    		List<Prodotto> prodotti = new ArrayList<>();
    		
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM "+TABLE_NAME;
        
        try
        {
        	ps = conn.prepareStatement(sql);
        
        	rs = ps.executeQuery();
        
        	while(rs.next())
        	{
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
        		p.setSconto(rs.getInt("sconto"));
        		
        		prodotti.add(p);
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

        return prodotti;
    }
}