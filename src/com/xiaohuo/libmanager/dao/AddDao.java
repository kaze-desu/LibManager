package com.xiaohuo.libmanager.dao;

import com.xiaohuo.libmanager.Init;
import com.xiaohuo.libmanager.db.DatabaseClose;
import com.xiaohuo.libmanager.db.DatabaseConnect;
import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BaseBooks;
import com.xiaohuo.libmanager.services.template.Book;
import com.xiaohuo.libmanager.services.template.Journal;
import com.xiaohuo.libmanager.services.template.Newspaper;

import java.sql.*;
import java.util.*;

/**
 * Add books to database.
 * @author Xiaohuo (Wang Boyun)
 */

public class AddDao
{
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private final String table = "BookList";
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
        String sql = "CREATE TABLE IF NOT EXISTS BookList (BookID INT PRIMARY KEY AUTO_INCREMENT,Type VARCHAR(255) NOT NULL ,Tittle VARCHAR(255) NOT NULL,Author VARCHAR(255) NOT NULL,Publisher VARCHAR(255) NOT NULL,Category VARCHAR(255) NOT NULL)";
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
            if(0<columnList.size())
            {
                try
                {
                    for (String s : columnList)
                    {
                        String addSql = "ALTER TABLE " + table + " ADD " + s + " VARCHAR(255)";
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
                int[] result = pstmt.executeBatch();
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
                //Check if the test mode is on.

            }
            catch (SQLException e)
            {
                e.printStackTrace();
                exceptions.add(e);
            }
        }
        int [] result;
        try
        {
            result = pstmt.executeBatch();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        if(testMode) {System.out.println("BookSQL Updated: "+ Arrays.toString(result));}
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }

    }
    /**
     * Add books to database.
     * @Type: Book
     * @param booksInfo List of books.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public void addBook(ArrayList<BaseBooks> booksInfo) throws CollectionException
    {
        ArrayList<String>columnList = new ArrayList<>();

        //Check if the table has the column Isbn.
        String columnSql = "SHOW COLUMNS FROM "+table+" LIKE ?";
        columnList.add("Isbn");

        //Add books to the table.
        String bookSql = "INSERT INTO "+table+" (Type,Tittle,Author,Publisher,Category,Isbn) VALUES (?,?,?,?,?,?)";
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
        for (BaseBooks baseBooks : booksInfo)
        {
            //Cast the object to Book, because we need the Isbn which only in book class.
            Book book = (Book) baseBooks;
            ArrayList<String> bookList = book.getBookInfo();
            list.put(booksInfo.indexOf(book),bookList);
            //Call the add method.
            add(columnSql,columnList,bookSql,list);
        }
    }

    /**
     * Add journal to database.
     * @Type: Journal
     * @param journalInfo List of journals.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public void addJournal(ArrayList<BaseBooks>journalInfo)throws CollectionException
    {
        ArrayList<String>columnList = new ArrayList<>();

        //Check if the table has the column Issn.
        String columnSql = "SHOW COLUMNS FROM "+table+" LIKE ?";
        columnList.add("Issn");

        //Add journals to the table.
        String journalSql = "INSERT INTO "+table+" (Type,Tittle,Author,Publisher,Category,Issn) VALUES (?,?,?,?,?,?)";
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
        for (BaseBooks baseBooks : journalInfo)
        {
            Journal journal = (Journal) baseBooks;
            ArrayList<String> bookList = journal.getBookInfo();
            list.put(journalInfo.indexOf(journal),bookList);
            //Call the add method.
            add(columnSql,columnList,journalSql,list);
        }
    }

    /**
     * Add newspaper to database.
     * @Type: Newspaper
     * @param newspaperInfo List of newspapers.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public void addNewspaper(ArrayList<BaseBooks>newspaperInfo)throws CollectionException
    {
        ArrayList<String>columnList = new ArrayList<>();

        //Check if the table has the column Issn and Copyright.
        String columnSql = "SHOW COLUMNS FROM "+table+" LIKE ?";
        columnList.add("Issn");
        columnList.add("CopyRight");

        //Add books to the table.
        String newspaperSql = "INSERT INTO "+table+" (Type,Tittle,Author,Publisher,Category,Issn,CopyRight) VALUES (?,?,?,?,?,?,?)";
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
        for (BaseBooks baseBooks : newspaperInfo)
        {
            Newspaper newspaper = (Newspaper) baseBooks;
            ArrayList<String> newspaperList = newspaper.getBookInfo();
            list.put(newspaperInfo.indexOf(newspaper),newspaperList);
            //Call the add method.
            add(columnSql,columnList,newspaperSql,list);
        }
    }
}