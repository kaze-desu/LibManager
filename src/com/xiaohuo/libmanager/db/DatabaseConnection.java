package com.xiaohuo.libmanager.db;

import com.xiaohuo.libmanager.exception.CollectionException;

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
     * @throws CollectionException throw a collection of exception
     */
    Connection connect() throws CollectionException;
}
