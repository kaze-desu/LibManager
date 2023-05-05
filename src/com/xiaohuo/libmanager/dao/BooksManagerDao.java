package com.xiaohuo.libmanager.dao;

import com.xiaohuo.libmanager.Init;
import com.xiaohuo.libmanager.db.DatabaseClose;
import com.xiaohuo.libmanager.db.DatabaseConnect;
import com.xiaohuo.libmanager.exception.CollectionException;

import java.sql.*;
import java.util.*;

/**
 * Manage all books in the library.
 * @author Xiaohuo(Wang Boyun)
 * @version 2.0 (Refactor version)
 */

public class BooksManagerDao
{


    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    //Table name should not include any capital letter.
    public static final String BOOK_TABLE = "bookslist";
    private boolean init = false;
    /**
     * Initiate the table.
     * @throws CollectionException CollectionException
     */
    public void initTable() throws CollectionException
    {
        //Check the test mode flag.
        boolean testMode = new Init().getTestMode();
        //Check if the table exists.
        List<Throwable> exceptions = new ArrayList<>();
        conn = DatabaseConnect.connect();
        String sql = "CREATE TABLE IF NOT EXISTS "+BOOK_TABLE+" (BookID INT PRIMARY KEY AUTO_INCREMENT,Type VARCHAR(255) NOT NULL ,Tittle VARCHAR(255) NOT NULL,Author VARCHAR(255) NOT NULL,Publisher VARCHAR(255) NOT NULL,Category VARCHAR(255) NOT NULL);";
        try
        {
            //Create table if not exists.
            pstmt = conn.prepareStatement(sql);
            int update = pstmt.executeUpdate();
            if(testMode){System.out.println("Table created, updated: "+update+"rows");}
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }finally
        {
            //Close connection.
            DatabaseClose.close(pstmt,conn);
        }
        //Initiate flag.
        init = true;
        //Throw exceptions if any.
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
    public void add(ArrayList<String> columnList, String bookSql, Map<Integer,ArrayList<String>> bookList) throws CollectionException
    {
        conn = DatabaseConnect.connect();
        //Check the test mode flag.
        boolean testMode = new Init().getTestMode();
        List<Throwable> exceptions = new ArrayList<>();
        //Check if the table is initiated.
        if (!init)
        {
            exceptions.add(new Exception("Table not initiated, please call initTable() first."));
            throw new CollectionException(exceptions);
        }
        //Check if the table has the column.
        ArrayList<String>columnNullList = new ArrayList<>();
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
            addColumn(conn,columnNullList);
        }
        //Add books to the table.
        addBooks(conn,bookSql,bookList);
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
        //Close connection.
        DatabaseClose.close(pstmt,conn);
    }
    /**
     * Add Column if to database.
     * @param conn Connection
     * @param columnList List of columns.
     * @throws CollectionException Exception thrown when there is any error.
     */
    private void addColumn(Connection conn,ArrayList<String> columnList) throws CollectionException
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
    private void addBooks(Connection conn,String bookSql, Map<Integer,ArrayList<String>> bookList) throws CollectionException
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
                        sql.append("'").append(information).append("'").append(",");
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
     * @return A list of books.
     * @throws CollectionException Exception thrown when there is any error.
     */

    public Map<Integer,ArrayList<String>>search(String value) throws CollectionException
    {
        List<Throwable>exceptions = new ArrayList<>();
        Set<Integer> bookIdSet = new TreeSet<>();
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
            String sql = "SELECT * FROM "+BOOK_TABLE+" LIMIT 1";
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
            rs = pstmt.executeQuery();
            rsData = rs.getMetaData();
            columnsNum = rsData.getColumnCount();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        for (int i = 1;i<=columnsNum;i++)
        {
            String sql = "SELECT * FROM "+BOOK_TABLE+" WHERE ? = ?";
            String columnName;
            try
            {
                columnName = rsData.getColumnName(i);
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,columnName);
                pstmt.setString(2,value);
                try
                {
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        if(!bookIdSet.contains(rs.getInt(1)))
                        {
                            ArrayList<String>bookList = new ArrayList<>();
                            bookIdSet.add(rs.getInt(1));
                            for (int j=1;j<=columnsNum;j++)
                            {
                                bookList.add(rs.getString(j+1));
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
        DatabaseClose.close(pstmt,conn,rs);
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
        return list;
    }

    /**
     * Advanced search for a specific column and value.
     * @param column The way to search.
     * @param value The value to search for.
     * @return A map of books that match the search.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public Map<Integer,ArrayList<String>>search(String column,String value) throws CollectionException
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
            String sql = "SELECT * FROM "+BOOK_TABLE+ " WHERE %s LIKE '?'";
            sql = String.format(sql,column);
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
                    for (int j=1;j<rs.getMetaData().getColumnCount();j++)
                    {
                        bookList.add(rs.getString(j+1));
                    }
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
            DatabaseClose.close(pstmt,conn,rs);
        }
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
        return list;
    }
}
