package com.xiaohuo.libmanager;

import com.xiaohuo.libmanager.Exception.CustomException;
import com.xiaohuo.libmanager.db.DatabaseConnectionImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class main
{
    public static void main(String[] args) throws CustomException
    {
        System.out.println("Installed Check...");
        Init init = new Init();
        System.out.println("Installed Check Complete");
        DatabaseConnectionImpl databaseConnection = new DatabaseConnectionImpl();
        try
        {
            databaseConnection.connect();
        }
        catch (CustomException e)
        {
            System.out.println("Connect Error, please check your database information");
            e.printStackTrace();
        }


    }
}
