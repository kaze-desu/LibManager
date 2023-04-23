package com.xiaohuo.libmanager.db;

import com.xiaohuo.libmanager.Exception.CustomException;

import java.sql.Connection;
/**
 * Database connection interface
 * @author xiaohuo(Wang Boyun)
 */
public interface DatabaseConnection
{
    /**
     * Connect to database
     * @return Connection return a connection thread
     * @throws CustomException throw a collection of exception
     */
    Connection connect() throws CustomException;
}
