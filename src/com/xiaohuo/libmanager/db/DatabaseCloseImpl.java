package com.xiaohuo.libmanager.db;

import com.xiaohuo.libmanager.Exception.CustomException;

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
public class DatabaseCloseImpl implements DatabaseClose
{
    /**
     * Close database connection in no ResultSet case.
     * @param pstmt PreparedStatement
     * @param conn Connection
     */
    @Override
    public void close(PreparedStatement pstmt, Connection conn) throws CustomException
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
            throw new CustomException(exceptions);
        }
    }
    /**
     * Close database connection in ResultSet case.
     * @param pstmt PreparedStatement
     * @param rs ResultSet
     * @param conn Connection
     */
    @Override
    public void close(PreparedStatement pstmt, ResultSet rs, Connection conn) throws CustomException
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
            throw new CustomException(exceptions);
        }
    }


}
