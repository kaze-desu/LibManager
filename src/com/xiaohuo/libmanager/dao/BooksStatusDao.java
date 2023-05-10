package com.xiaohuo.libmanager.dao;

import com.xiaohuo.libmanager.Init;
import com.xiaohuo.libmanager.db.DatabaseClose;
import com.xiaohuo.libmanager.db.DatabaseConnect;
import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.status.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * To sign the book status
 * @author Xiaohuo(Wang Bouyn)
 */
public class BooksStatusDao
{
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
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
        /*
         * StatusID : Unique value with each status
         * BookId : The bookID, which should bind with the book table
         * Location : The location of the book
         * Status : The status of the book, true means the book is available, false means the book is not available.
         */
        String tableSql = "StatusID INT PRIMARY KEY AUTO_INCREMENT,BookID INT(255) NOT NULL , Location VARCHAR(255) NOT NULL, Status BOOLEAN NOT NULL";
        initTableDao.initTable(BOOK_TABLE,tableSql);
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }

    public void addStatus(int bookId, String location, boolean status) throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        boolean testMode = new Init().getTestMode();
        String sql = "INSERT INTO %s (BookID,Location,Status) VALUES(?,?,?)";
        sql = String.format(sql,BOOK_TABLE);
        try
        {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,bookId);
            pstmt.setString(2,location);
            pstmt.setBoolean(3,status);
            int update = pstmt.executeUpdate();
            if(testMode){System.out.println("update row:"+update);}
        }
        catch (SQLException e)
        {
            exceptions.add(e);
        }
        finally
        {
            DatabaseClose.close(conn,pstmt);
        }
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }

    /**
     * search the status by bookID
     * @param bookId bookId
     * @return ArrayList<Status> : 0 is the bookID, 1 is the location, 2 is the status.
     * @throws CollectionException CollectionException
     */
    public Map<Integer,ArrayList<Status>> searchStatus(int bookId)throws CollectionException
    {
        List<Throwable>exceptions = new ArrayList<>();
        Map<Integer,ArrayList<Status>> list = new HashMap<>(1000);
        try
        {
            conn = DatabaseConnect.connect();

        }
        catch (CollectionException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        try
        {
            String sql = "SELECT * FROM %s WHERE BookID LIKE ?";
            sql = String.format(sql,BOOK_TABLE);
            pstmt = conn.prepareStatement(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        try
        {
            pstmt.setInt(1,bookId);
            try
            {
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    //Add the information of the book to the list.
                    ArrayList<Status> statusList = new ArrayList<>();
                    statusList.add(new Status(rs.getInt(1),rs.getString(3),rs.getBoolean(4)));
                    //Add the BookID to Map.
                    list.put(rs.getInt(1),statusList);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                exceptions.add(e);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        finally
        {
            DatabaseClose.close(conn,pstmt,rs);
        }
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
        return list;
    }
}
