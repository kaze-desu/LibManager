package com.xiaohuo.libmanager.dao;

import com.xiaohuo.libmanager.Init;
import com.xiaohuo.libmanager.db.DatabaseClose;
import com.xiaohuo.libmanager.db.DatabaseConnect;
import com.xiaohuo.libmanager.exception.CollectionException;

import java.net.IDN;
import java.security.spec.PSSParameterSpec;
import java.sql.*;
import java.util.*;

/**
 * Manage all books in the library.
 * @author Xiaohuo(Wang Boyun)
 * @version 2.0 (Refactor version)
 */

public class BooksManageDao
{


    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    //Table name should not include any capital letter.
    public static final String BOOK_TABLE = "books_list";
    /**
     * Initiate the table.
     */
    public BooksManageDao() throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        try
        {
            conn = DatabaseConnect.connect();
            InitTableDao initTableDao = new InitTableDao(conn);
            String sql = "BookID INT PRIMARY KEY AUTO_INCREMENT,Type VARCHAR(255) NOT NULL ,Tittle VARCHAR(255) NOT NULL,Author VARCHAR(255) NOT NULL,Publisher VARCHAR(255) NOT NULL,Category VARCHAR(255) NOT NULL";
            initTableDao.initTable(BOOK_TABLE,sql);
        }
        catch (CollectionException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }

    /**
     * Delete book in the table
     * @param ID the ID of the book that the user want to delete in the table
     * @throws CollectionException Exception thrown when there is any error.
     */
    public void delete(int ID) throws CollectionException
    {
        conn = DatabaseConnect.connect();

        List<Throwable> exceptions = new ArrayList<>();

        try{
            String sql = "DELETE FROM ? WHERE BookID=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,BOOK_TABLE);
            pstmt.setInt(2, ID);
            int update = pstmt.executeUpdate();
            if(update>=1){
                System.out.println("Delete success");
            }
            else{
                System.out.println("Delete fail");
            }
        }
        catch (SQLException e)
        {
            exceptions.add(e);
        }
        finally {
            DatabaseClose.close(conn,pstmt);
        }
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }

    /**
     * Add books to database in general.
     * @param columnList List of columns.
     * @param bookSql SQL for adding books.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public void addBook(ArrayList<String> columnList, String bookSql, Map<Integer,ArrayList<String>> bookList) throws CollectionException
    {
        //Check the test mode flag.
        boolean testMode = new Init().getTestMode();
        List<Throwable> exceptions = new ArrayList<>();
        //Check if the table has the column.
        ArrayList<String>columnNullList = new ArrayList<>();
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
            for (String s : columnList)
            {
                String sql = "SHOW COLUMNS FROM "+BOOK_TABLE+" LIKE '%s'";
                sql = String.format(sql,s);
                pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next())
                {
                    columnNullList.add(s);
                }
                if (testMode)
                {
                    System.out.println("try to check column: " + s);
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }

        //If not, add the column.
        if(columnNullList.size()>0)
        {
            addBookColumn(conn,columnNullList);
        }
        //Add books to the table.
        addBookProcession(conn,bookSql,bookList);
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
        //Close connection.
        DatabaseClose.close(conn,pstmt);
    }
    /**
     * Add Column if to database when the column is not exist.
     * @param conn Connection
     * @param columnList List of columns.
     * @throws CollectionException Exception thrown when there is any error.
     */
    private void addBookColumn(Connection conn, ArrayList<String> columnList) throws CollectionException
    {
        ArrayList<Throwable>exceptions = new ArrayList<>();
        boolean testMode = new Init().getTestMode();
        try
        {
            //Set auto commit to false to use batch.
            conn.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        try
        {
            for (String s : columnList)
            {
                String sql = "ALTER TABLE %s ADD %s VARCHAR(255)";
                sql = String.format(sql,BOOK_TABLE,s);
                try
                {
                    pstmt = conn.prepareStatement(sql);
                    pstmt.addBatch();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                    exceptions.add(e);
                }
            }
            //Execute the batch.
            int[] result = pstmt.executeBatch();
            //Commit the changes.
            conn.commit();
            //Clear the batch.
            pstmt.clearBatch();

            //Check if the test mode is on.
            if(testMode){System.out.println("created: "+result.length+" Columns");}
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
    }

    /**
     * Add books to the table.
     * @param conn Connection
     * @param bookSql SQL for adding books.
     * @param bookList List of books.
     * @throws CollectionException Exception thrown when there is any error.
     */
    private void addBookProcession(Connection conn, String bookSql, Map<Integer,ArrayList<String>> bookList) throws CollectionException
    {
        boolean testMode = new Init().getTestMode();
        List<Throwable> exceptions = new ArrayList<>();
        try
        {
            //Set auto commit to false to use batch.
            conn.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            exceptions.add(e);
        }

        //The batch could not use the loop or Iterator after the addBatch() function (I guess), so I use a count variable rather than the key in the loop.
        int count = 0;
        try
        {
            //SQL injection possible.
            Statement stmt = conn.createStatement();
            for (Integer key : bookList.keySet())
            {
                try
                {
                    StringBuilder sql = new StringBuilder(bookSql + "(");
                    for (String information:bookList.get(key))
                    {
                        sql.append("'").append(information)
                                .append("'").append(",");
                        if(bookList.get(key).indexOf(information)==bookList.get(key).size()-1)
                        {
                            sql = new StringBuilder(sql.substring(0, sql.length() - 1));
                            sql.append(")");
                        }
                    }
                    stmt.addBatch(sql.toString());
                    count++;
                    if (count % 1000 == 0 || count == bookList.size())
                    {
                        int[] result = stmt.executeBatch();
                        conn.commit();
                        stmt.clearBatch();
                        if (testMode)
                        {
                            System.out.println("BookSQL Updated: " + Arrays.toString(result));
                        }
                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                    exceptions.add(e);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }

        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }


    /**
     *  Search the book by the value whether any column.
     * @param value The value to search.
     * @return A list of books. Integer is the bookID, and ArrayList is the information of the book.
     * @throws CollectionException Exception thrown when there is any error.
     */

    public Map<Integer,ArrayList<String>> searchBook(String value) throws CollectionException
    {
        List<Throwable>exceptions = new ArrayList<>();
        //The set is used to store the bookID to avoid duplicate.
        Set<Integer> bookIdSet = new TreeSet<>();
        //The map is used to store the bookID and the information of the book.
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
        int columnsNum = 0;
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
            //Get the columns number.(Limit 1 to avoid extra time)
            String sql = "SELECT * FROM %s LIMIT 1";
            sql = String.format(sql,BOOK_TABLE);
            pstmt = conn.prepareStatement(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        ResultSetMetaData rsData = null;
        try
        {
            //Get the raw data.
            rs = pstmt.executeQuery();
            rsData = rs.getMetaData();
            //Get the columns number.
            columnsNum = rsData.getColumnCount();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        //Search the value in each column.
        for (int i = 1;i<=columnsNum;i++)
        {
            try
            {
                String sql = "SELECT * FROM %s WHERE %s = ?";
                String columnName = rsData.getColumnName(i);
                sql = String.format(sql,BOOK_TABLE,columnName);
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,value);
                try
                {
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        //If the bookID is not in the set, add the book to the map.
                        if(!bookIdSet.contains(rs.getInt(1)))
                        {
                            ArrayList<String>bookList = new ArrayList<>();
                            bookIdSet.add(rs.getInt(1));
                            //Add the information of the book to the list.
                            for (int j=1;j<columnsNum;j++)
                            {
                                try
                                {
                                    bookList.add(rs.getString(j+1));
                                }
                                catch (SQLException e)
                                {
                                    e.printStackTrace();
                                    exceptions.add(e);
                                }
                            }
                            list.put(rs.getInt(1),bookList);
                        }

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
        }
        DatabaseClose.close(conn,pstmt,rs);
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
        return list;
    }

    /**
     * Advanced search for a specific column and value.
     * ArrayList: 0 is the Type, 1 is the Tittle. 2 is the Author, 3 is the Publisher, 4 is the Category, 5 is the Isbn/Issn.
     * @param column The way to search.
     * @param value The value to search for.
     * @return A map of books that match the search. Integer is bookID, and ArrayList is the information of the book.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public Map<Integer,ArrayList<String>> searchBook(String column, String value) throws CollectionException
    {
        List<Throwable>exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
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
            String sql = "SELECT * FROM %s WHERE %s LIKE ?";
            sql = String.format(sql,BOOK_TABLE,column);
            pstmt = conn.prepareStatement(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        try
        {
            pstmt.setString(1,"%"+value+"%");
            try
            {
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    ArrayList<String>bookList = new ArrayList<>();
                    //Add the information of the book to the list.
                    for (int j=1;j<rs.getMetaData().getColumnCount();j++)
                    {
                        if(rs.getString(j+1)!=null)
                        {
                            bookList.add(rs.getString(j+1));
                        }
                    }
                    //Add the BookID to Map.
                    list.put(rs.getInt(1),bookList);
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
