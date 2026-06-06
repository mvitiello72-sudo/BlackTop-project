package model.dao;

import model.MetodoPagamento;
import model.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagamentoDAO
{
    private static final String TABLE_NAME = "metodo_pagamento";

    //INSERT
    public void doSave(MetodoPagamento m) throws SQLException
    {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;

        String sql =
                "INSERT INTO " + TABLE_NAME +
                " (tipo, numero_carta, intestatario, scadenza, cvv, fk_utente) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setString(1, m.getTipo());
            ps.setString(2, m.getNumeroCarta());
            ps.setString(3, m.getIntestatario());
            ps.setDate(4, m.getScadenza());
            ps.setString(5, m.getCvv());
            ps.setInt(6, m.getFkUtente());

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
    public MetodoPagamento doRetrieveByKey(int idMetodo) throws SQLException
    {
        MetodoPagamento m = null;

        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
                "SELECT * FROM " + TABLE_NAME +
                " WHERE id_metodo = ?";

        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idMetodo);

            rs = ps.executeQuery();

            if(rs.next())
            {
                m = new MetodoPagamento();

                m.setIdMetodo(rs.getInt("id_metodo"));
                m.setTipo(rs.getString("tipo"));
                m.setNumeroCarta(rs.getString("numero_carta"));
                m.setIntestatario(rs.getString("intestatario"));
                m.setScadenza(rs.getDate("scadenza"));
                m.setCvv(rs.getString("cvv"));
                m.setFkUtente(rs.getInt("fk_utente"));
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

        return m;
    }

    //SELECT BY UTENTE
    public List<MetodoPagamento> doRetrieveByUtente(int fkUtente) throws SQLException
    {
        List<MetodoPagamento> metodi = new ArrayList<>();

        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
                "SELECT * FROM " + TABLE_NAME +
                " WHERE fk_utente = ?";

        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, fkUtente);

            rs = ps.executeQuery();

            while(rs.next())
            {
                MetodoPagamento m = new MetodoPagamento();

                m.setIdMetodo(rs.getInt("id_metodo"));
                m.setTipo(rs.getString("tipo"));
                m.setNumeroCarta(rs.getString("numero_carta"));
                m.setIntestatario(rs.getString("intestatario"));
                m.setScadenza(rs.getDate("scadenza"));
                m.setCvv(rs.getString("cvv"));
                m.setFkUtente(rs.getInt("fk_utente"));

                metodi.add(m);
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

        return metodi;
    }
    
    //UPDATE
    public void doUpdate(MetodoPagamento m) throws SQLException
    {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;

        String sql =
                "UPDATE " + TABLE_NAME +
                " SET tipo=?, numero_carta=?, intestatario=?, scadenza=?, cvv=?, fk_utente=? " +
                "WHERE id_metodo=?";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setString(1, m.getTipo());
            ps.setString(2, m.getNumeroCarta());
            ps.setString(3, m.getIntestatario());
            ps.setDate(4, m.getScadenza());
            ps.setString(5, m.getCvv());
            ps.setInt(6, m.getFkUtente());
            ps.setInt(7, m.getIdMetodo());

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
    public void doDelete(int idMetodo) throws SQLException
    {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;

        String sql =
                "DELETE FROM " + TABLE_NAME +
                " WHERE id_metodo = ?";

        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idMetodo);

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