package model.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtils
{
    private PasswordUtils()
    {
        throw new UnsupportedOperationException("Questa è una classe di utility e non può essere istanziata.");
    }

    /*
     * Riceve una password in chiaro e restituisce il suo hash SHA-512 in formato esadecimale.
     * Come parametro è la password in chiaro da cifrare
     * ritorna la stringa esadecimale dell'hash
     */
    public static String toDigest(String password)
    {
        if (password == null)
        {
            return null;
        }
        
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            
            // Calcolo il digest della password convertita in byte (UTF-8)
            byte[] digestBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            
            // Converto il digest binario in una stringa esadecimale
            StringBuilder sb = new StringBuilder();
            for (byte b : digestBytes)
            {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
            
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Errore critico: Algoritmo SHA-512 non disponibile nel sistema.", e);
        }
    }
}