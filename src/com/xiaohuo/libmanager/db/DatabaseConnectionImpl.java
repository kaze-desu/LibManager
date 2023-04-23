package com.xiaohuo.libmanager.db;
import com.xiaohuo.libmanager.Exception.CustomException;
import com.xiaohuo.libmanager.Init;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public class DatabaseConnectionImpl implements DatabaseConnection
{

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    @Override
    public Connection connect() throws CustomException
    {
        Connection conn = null;
        List<Throwable> exceptions = new ArrayList<>();
        Init init = null;
        try
        {
            init = new Init();
        }
        catch (CustomException e)
        {
            exceptions.add(e);
        }
        try
        {
            Class.forName(JDBC_DRIVER);
        }
        catch (ClassNotFoundException e)
        {
            exceptions.add(e);
        }
        assert init != null;
        String username = init.getUsername();
        String password = init.getPassword();
        String address = init.getAddress();
        String databaseName = init.getDatabaseName();
        String rootUrl = "jdbc:mysql://"+address+"/?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        String url = "jdbc:mysql://"+address+"/"+databaseName+"/?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";

        // Open a connection with root database to check root database exist.
        try
        {
            conn = DriverManager.getConnection(rootUrl, username, password);
        }
        catch (SQLException e)
        {
            System.out.println("Connect Error, please check your database information");
            exceptions.add(e);
            throw new CustomException(exceptions);
        }
        finally
        {
            assert conn != null;
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                exceptions.add(e);
            }
        }

        //Open a connection with database to check if database exist
        try
        {
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            System.out.println("Database not exist, creating database...");
            try
            {
                //TODO Using ResultSet to check if database exist, if not create database
                conn = DriverManager.getConnection(rootUrl, username, password);
            }
            catch (SQLException ex)
            {
                throw new RuntimeException(ex);
            }
        }
        if (exceptions.size() > 0)
        {
            throw new CustomException(exceptions);
        }
        return conn;
    }
}

