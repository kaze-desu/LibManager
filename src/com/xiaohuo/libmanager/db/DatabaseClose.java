package com.xiaohuo.libmanager.db;

import com.xiaohuo.libmanager.exception.CollectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * Close database connection interface
 * @author xiaohuo(Wang Boyun)
 */
public interface DatabaseClose {
    /**
     * Close database connection in no ResultSet case.
     * @param pstmt PreparedStatement
     * @param conn Connection
     * @throws CollectionException throw a collection of exception
     */
    void close(PreparedStatement pstmt, Connection conn) throws CollectionException;
    /**
     * Close database connection in ResultSet case.
     * @param pstmt PreparedStatement
     * @param rs ResultSet
     * @param conn Connection
     * @throws CollectionException throw a collection of exception
     */
    void close(PreparedStatement pstmt, ResultSet rs, Connection conn) throws CollectionException;
}
