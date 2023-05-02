package com.xiaohuo.libmanager.dao;

import com.xiaohuo.libmanager.Init;
import com.xiaohuo.libmanager.db.DatabaseClose;
import com.xiaohuo.libmanager.db.DatabaseConnect;
import com.xiaohuo.libmanager.exception.CollectionException;

import java.sql.*;
import java.util.*;

/**
 * Add books to database.
 * @author Xiaohuo (Wang Boyun)
 */

public class BooksManagerDao
{
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    public static final String BOOK_TABLE = "BooksList";
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
        String sql = "CREATE TABLE IF NOT EXISTS "+BOOK_TABLE+" (BookID INT PRIMARY KEY AUTO_INCREMENT,Type VARCHAR(255) NOT NULL ,Tittle VARCHAR(255) NOT NULL,Author VARCHAR(255) NOT NULL,Publisher VARCHAR(255) NOT NULL,Category VARCHAR(255) NOT NULL)";
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
     * @param columnSql SQL for checking if the table has the column.
     * @param columnList List of columns.
     * @param bookSql SQL for adding books.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public void add(String columnSql,ArrayList<String> columnList,String bookSql,Map<Integer,ArrayList<String>> bookList) throws CollectionException
    {
        //TODO Try to use ResultSetMetaData to instead of get Row/Column function which need use a loop before.

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
            pstmt = conn.prepareStatement(columnSql);

            for (String s : columnList)
            {
                pstmt.setString(1, s);
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
    private void addColumn(Connection conn,ArrayList<String> columnList) throws CollectionException
    {
        ArrayList<Throwable>exceptions = new ArrayList<>();
        boolean testMode = new Init().getTestMode();
        try
        {
            conn.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        if(0<columnList.size())
            {
                try
                {
                    for (String s : columnList)
                    {
                        String addSql = "ALTER TABLE " + BOOK_TABLE + " ADD " + s + " VARCHAR(255)";
                        try
                        {
                            pstmt = conn.prepareStatement(addSql);
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
                if(testMode){System.out.println("Ran columnSQL and updated: "+result.length+" rows");}
            }
                catch (SQLException e)
                {
                    e.printStackTrace();
                    exceptions.add(e);
                }
        }

        if(exceptions.size()>1)
        {
            throw new CollectionException(exceptions);
        }
    }
    private void addBooks(Connection conn, String bookSql, Map<Integer,ArrayList<String>> bookList) throws CollectionException
    {
        boolean testMode = new Init().getTestMode();
        List<Throwable> exceptions = new ArrayList<>();
        try
        {
            conn.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            exceptions.add(e);
        }
        int count = 0;
        for (int i = 0;i<bookList.size();i++)
        {
            try
            {
                pstmt = conn.prepareStatement(bookSql);
                ArrayList<String>list = bookList.get(i);
                for (int j = 0;j<list.size();j++)
                {
                    pstmt.setString(j+1,list.get(j));
                    if(testMode){System.out.println("Adding :"+list.get(j));}
                }
                pstmt.addBatch();
                if(testMode){System.out.println("Will add bookList :"+bookList.get(i));}
                count++;

            }
            catch (SQLException e)
            {
                e.printStackTrace();
                exceptions.add(e);
            }

            if (count>1000)
            {
                int [] result;
                try
                {
                    result = pstmt.executeBatch();
                    conn.commit();
                    pstmt.clearBatch();
                    count = 0;
                    if(testMode) {System.out.println("BookSQL Updated: "+ Arrays.toString(result));}
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                    exceptions.add(e);
                }
            }
        }
        if (count>0)
        {
            int [] result;
            try
            {
                result = pstmt.executeBatch();
                conn.commit();
                pstmt.clearBatch();
                if(testMode) {System.out.println("BookSQL Updated: "+ Arrays.toString(result));}
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                exceptions.add(e);
            }
        }
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }

    }

    public Map<Integer,ArrayList<String>> search(String column,String value) throws CollectionException
    {
        List<Throwable>exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
        int bookId;
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
        String sql = "SELECT * FROM "+BOOK_TABLE+" WHERE ? = ?";
        try
        {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,column);
            pstmt.setString(2,value);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        try
        {
            rs = pstmt.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        try
        {
            ResultSetMetaData rsData = rs.getMetaData();
            columnsNum = rsData.getColumnCount();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        try
        {
            try
            {
                while(rs.next())
                {
                    ArrayList<String> bookInfo = new ArrayList<>();
                    for (int i = 0;i<columnsNum;i++)
                    {
                        bookInfo.add(rs.getString(i+2));
                    }
                    list.put(rs.getInt(1),bookInfo);
                }
            } catch (SQLException e)
            {
                exceptions.add(e);
            }
        }
        finally
        {
            try
            {
                DatabaseClose.close(pstmt,conn,rs);
            }
            catch (CollectionException e)
            {
                e.printStackTrace();
                exceptions.add(e);
            }
        }
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
        return list;
    }

}