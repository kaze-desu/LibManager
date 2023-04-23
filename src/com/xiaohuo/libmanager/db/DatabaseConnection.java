package com.xiaohuo.libmanager.db;

import com.xiaohuo.libmanager.Exception.CustomException;

import java.sql.Connection;

public interface DatabaseConnection
{
    /**
     * Connect to database
     */
    Connection connect() throws CustomException;
}
