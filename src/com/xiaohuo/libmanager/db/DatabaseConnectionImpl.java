package com.xiaohuo.libmanager.db;
import com.xiaohuo.libmanager.Exception.CustomException;
import com.xiaohuo.libmanager.Init;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiaohuo(Wang Boyun)
 * Connect to database
 */
public class DatabaseConnectionImpl implements DatabaseConnection
{
    private Connection conn;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    @Override
    public Connection connect() throws CustomException
    {
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
        String url = "jdbc:mysql://"+address+"/"+databaseName+"?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";

        // Open a connection
        try
        {
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            System.out.println("Connect Error, please check your database information");
            exceptions.add(e);
        }
        if (exceptions.size() > 0)
        {
            throw new CustomException(exceptions);
        }
        return conn;
    }
}

