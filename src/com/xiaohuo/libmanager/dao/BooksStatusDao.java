package com.xiaohuo.libmanager.dao;

import com.xiaohuo.libmanager.db.DatabaseConnect;
import com.xiaohuo.libmanager.exception.CollectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * To sign the book status
 * @author Xiaohuo(Wang Bouyn)
 */
public class BooksStatusDao
{
    private Connection conn = null;
    private final PreparedStatement pstmt = null;
    private final ResultSet rs = null;
    //Table name should not include any capital letter.
    public static final String BOOK_TABLE = "books_status";
    private final boolean init = false;

    /**
     * Initiate the table.
     * @throws CollectionException CollectionException
     */
    public BooksStatusDao() throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        try
        {
            conn = DatabaseConnect.connect();

        }
        catch (CollectionException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        InitTableDao initTableDao = new InitTableDao(conn);
        String tableSql = "StatusID INT PRIMARY KEY AUTO_INCREMENT,BookId VARCHAR(255) NOT NULL , Isbn VARCHAR(255) NOT NULL, Issn VARCHAR(255) NOT NULL, location VARCHAR(255) NOT NULL, status VARCHAR(255) NOT NULL";
        initTableDao.initTable(BOOK_TABLE,tableSql);
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }

    public void addStatus()
    {

    }


}
