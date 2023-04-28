package com.xiaohuo.libmanager.db;

import com.xiaohuo.libmanager.exception.CollectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Close database connection
 * @author xiaohuo(Wang Boyun)
 */
public class DatabaseClose
{
    /**
     * Close database connection in no ResultSet case.
     * @param conn Connection
     */
    public static void close(Connection conn) throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        try
        {
            conn.close();
        }
        catch (SQLException e)
        {
            exceptions.add(e);
        }
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }

    /**
     * Close database connection in PreparedStatement case.
     * @param pstmt PreparedStatement
     * @param conn Connection
     * @throws CollectionException CollectionException
     */
    public static void close(PreparedStatement pstmt, Connection conn) throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        try
        {
            pstmt.close();
        }
        catch (SQLException e)
        {
            exceptions.add(e);
        }
        try
        {
            conn.close();
        }
        catch (SQLException e)
        {
            exceptions.add(e);
        }
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }
    /**
     * Close database connection in ResultSet case.
     * @param pstmt PreparedStatement
     * @param rs ResultSet
     * @param conn Connection
     */
    public static void close(PreparedStatement pstmt, Connection conn,ResultSet rs) throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        try
        {
            pstmt.close();
        }
        catch (SQLException e)
        {
            exceptions.add(e);
        }
        try
        {
            rs.close();
        }
        catch (SQLException e)
        {
            exceptions.add(e);
        }
        try
        {
            conn.close();
        }
        catch (SQLException e)
        {
            exceptions.add(e);
        }
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }


}
