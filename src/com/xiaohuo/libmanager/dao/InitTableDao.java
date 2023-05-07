package com.xiaohuo.libmanager.dao;

import com.xiaohuo.libmanager.Init;
import com.xiaohuo.libmanager.db.DatabaseClose;
import com.xiaohuo.libmanager.db.DatabaseConnect;
import com.xiaohuo.libmanager.exception.CollectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Initiate the table.
 * @author Xiaohuo(Wang Boyun)
 */
public class InitTableDao
{
    private Connection conn;
    private PreparedStatement pstmt;
    //Table name should not include any capital letter.
    InitTableDao(Connection conn)
    {
        this.conn = conn;
    }
    /**
     * Initiate the table.
     * @param tableName Table name
     * @param tableSql Table sql that use to crate table.
     * @throws CollectionException Collect the exception and throw it at one time.
     */
    public void initTable(String tableName,String tableSql) throws CollectionException
    {
        //Check the test mode flag.
        boolean testMode = new Init().getTestMode();
        //Check if the table exists.
        List<Throwable> exceptions = new ArrayList<>();
        conn = DatabaseConnect.connect();
        String sql = "CREATE TABLE IF NOT EXISTS %s (%s)";
        sql = String.format(sql,tableName,tableSql);
        try
        {
            //Create table if not exists.
            pstmt = conn.prepareStatement(sql);
            int update = pstmt.executeUpdate();
            if(testMode){System.out.println("Table created, updated: "+update+"rows");}
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }finally
        {
            //Close connection.
            DatabaseClose.close(pstmt,conn);
        }
        //Throw exceptions if any.
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }
}
