package com.xiaohuo.libmanager.db;
import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.Init;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a function for connecting to database.
 * @author Xiaohuo (Wang Boyun)
 */
public class DatabaseConnect
{
    private static Connection conn;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static Connection connect() throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        Init init = null;
        try
        {
            init = new Init();
        }
        catch (CollectionException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        try
        {
            Class.forName(JDBC_DRIVER);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        assert init != null;
        String username = init.getUsername();
        String password = init.getPassword();
        String address = init.getAddress();
        String databaseName = init.getDatabaseName();
        String url = "jdbc:mysql://"+address+"/"+databaseName+"?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";

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
        // Throw all the exceptions
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
        return conn;
    }
    public static boolean check(String username, String password, String address, String databaseName) throws CollectionException
    {
        String url = "jdbc:mysql://"+address+"/"+databaseName+"?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        try
        {
            conn = DriverManager.getConnection(url, username, password);
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Connect Error, please check your database information");
            e.printStackTrace();
            return false;
        }
        finally
        {
            DatabaseClose.close(conn);
        }

    }
}

