package model.dao;

import model.Immagine;
import model.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImmagineDAO
{
    private static final String TABLE_NAME = "immagine";

    //INSERT
    public void doSave(Immagine i) throws SQLException
    {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;

        String sql =
                "INSERT INTO " + TABLE_NAME +
                " (percorso_immagine, fk_prodotto) " +
                "VALUES (?, ?)";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setString(1, i.getPercorsoImmagine());
            ps.setInt(2, i.getFkProdotto());

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
    public Immagine doRetrieveByKey(int idImmagine) throws SQLException
    {
        Immagine i = null;

        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
                "SELECT * FROM " + TABLE_NAME +
                " WHERE id_immagine = ?";

        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idImmagine);

            rs = ps.executeQuery();

            if(rs.next())
            {
                i = new Immagine();

                i.setIdImmagine(rs.getInt("id_immagine"));
                i.setPercorsoImmagine(rs.getString("percorso_immagine"));
                i.setFkProdotto(rs.getInt("fk_prodotto"));
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

    //SELECT BY PRODOTTO
    public List<Immagine> doRetrieveByProdotto(int fkProdotto) throws SQLException
    {
        List<Immagine> immagini = new ArrayList<>();

        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
                "SELECT * FROM " + TABLE_NAME +
                " WHERE fk_prodotto = ?";

        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, fkProdotto);

            rs = ps.executeQuery();

            while(rs.next())
            {
                Immagine i = new Immagine();

                i.setIdImmagine(rs.getInt("id_immagine"));
                i.setPercorsoImmagine(rs.getString("percorso_immagine"));
                i.setFkProdotto(rs.getInt("fk_prodotto"));

                immagini.add(i);
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

        return immagini;
    }

    //DELETE
    public void doDelete(int idImmagine) throws SQLException
    {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;

        String sql =
                "DELETE FROM " + TABLE_NAME +
                " WHERE id_immagine = ?";

        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idImmagine);

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