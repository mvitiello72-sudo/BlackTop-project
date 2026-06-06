package model.dao;

import model.Fattura;
import model.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FatturaDAO
{
    private static final String TABLE_NAME = "fattura";

    //INSERT
    public void doSave(Fattura f) throws SQLException
    {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;

        String sql =
                "INSERT INTO " + TABLE_NAME +
                " (data_emissione, totale_fattura, fk_ordine) " +
                "VALUES (?, ?, ?)";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setDate(1, f.getDataEmissione());
            ps.setDouble(2, f.getTotaleFattura());
            ps.setInt(3, f.getFkOrdine());

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
    public Fattura doRetrieveByKey(int idFattura) throws SQLException
    {
        Fattura f = null;

        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
                "SELECT * FROM " + TABLE_NAME +
                " WHERE id_fattura = ?";

        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idFattura);

            rs = ps.executeQuery();

            if(rs.next())
            {
                f = new Fattura();

                f.setIdFattura(rs.getInt("id_fattura"));
                f.setDataEmissione(rs.getDate("data_emissione"));
                f.setTotaleFattura(rs.getDouble("totale_fattura"));
                f.setFkOrdine(rs.getInt("fk_ordine"));
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

        return f;
    }

    //SELECT BY ORDINE
    public Fattura doRetrieveByOrdine(int fkOrdine) throws SQLException
    {
        Fattura f = null;

        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
                "SELECT * FROM " + TABLE_NAME +
                " WHERE fk_ordine = ?";

        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, fkOrdine);

            rs = ps.executeQuery();

            if(rs.next())
            {
                f = new Fattura();

                f.setIdFattura(rs.getInt("id_fattura"));
                f.setDataEmissione(rs.getDate("data_emissione"));
                f.setTotaleFattura(rs.getDouble("totale_fattura"));
                f.setFkOrdine(rs.getInt("fk_ordine"));
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

        return f;
    }

    //DELETE (raramente usato, ma utile admin)
    public void doDelete(int idFattura) throws SQLException
    {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;

        String sql =
                "DELETE FROM " + TABLE_NAME +
                " WHERE id_fattura = ?";

        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idFattura);

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