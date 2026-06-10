package model.dao;

import model.MetodoPagamento;
import model.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagamentoDAO
{
    private static final String TABLE_NAME = "metodo_pagamento";

    //Imposta a false tutte le carte di un utente
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
    public void cambiaStatoPredefinito(int idUtente, int idMetodo) throws SQLException
    {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;

        String sql = "UPDATE " + TABLE_NAME + " SET predefinito = TRUE WHERE id_metodo = ?";

        try
        {
            // 1. Disattiva il flag predefinito su tutte le altre carte dell'utente usando la connessione corrente
            resetPredefinitoPerUtente(idUtente, conn);

            // 2. Attiva il flag predefinito sulla carta selezionata
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

    // INSERT
    public void doSave(MetodoPagamento m) throws SQLException
    {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;

        if (m.getPredefinito()) {
            resetPredefinitoPerUtente(m.getFkUtente(), conn);
        }

        String sql =
                "INSERT INTO " + TABLE_NAME +
                " (tipo, numero_carta, intestatario, scadenza, cvv, predefinito, fk_utente) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setString(1, m.getTipo());
            ps.setString(2, m.getNumeroCarta());
            ps.setString(3, m.getIntestatario());
            ps.setDate(4, m.getScadenza());
            ps.setString(5, m.getCvv()); 
            ps.setBoolean(6, m.getPredefinito());
            ps.setInt(7, m.getFkUtente());

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
    public MetodoPagamento doRetrieveByKey(int idMetodo) throws SQLException
    {
        MetodoPagamento m = null;

        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_metodo = ?";

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
                m.setPredefinito(rs.getBoolean("predefinito"));
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

    // SELECT BY UTENTE
    public List<MetodoPagamento> doRetrieveByUtente(int fkUtente) throws SQLException
    {
        List<MetodoPagamento> metodi = new ArrayList<>();

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
                MetodoPagamento m = new MetodoPagamento();

                m.setIdMetodo(rs.getInt("id_metodo"));
                m.setTipo(rs.getString("tipo"));
                m.setNumeroCarta(rs.getString("numero_carta"));
                m.setIntestatario(rs.getString("intestatario"));
                m.setScadenza(rs.getDate("scadenza"));
                m.setCvv(rs.getString("cvv")); 
                m.setPredefinito(rs.getBoolean("predefinito"));
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

    // UPDATE
    public void doUpdate(MetodoPagamento m) throws SQLException
    {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;

        if (m.getPredefinito()) {
            resetPredefinitoPerUtente(m.getFkUtente(), conn);
        }

        String sql =
                "UPDATE " + TABLE_NAME + " SET " +
                "tipo=?, numero_carta=?, intestatario=?, scadenza=?, cvv=?, predefinito=? " +
                "WHERE id_metodo=?";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setString(1, m.getTipo());
            ps.setString(2, m.getNumeroCarta());
            ps.setString(3, m.getIntestatario());
            ps.setDate(4, m.getScadenza());
            ps.setString(5, m.getCvv());
            ps.setBoolean(6, m.getPredefinito());
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

    // DELETE
    public void doDelete(int idMetodo) throws SQLException
    {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;

        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id_metodo = ?";

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