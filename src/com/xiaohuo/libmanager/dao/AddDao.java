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
import java.util.ArrayList;
import java.util.List;

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
        String sql = "CREATE TABLE IF NOT EXISTS BookList (BookID INT PRIMARY KEY AUTO_INCREMENT,Tittle VARCHAR(255) NOT NULL,Author VARCHAR(255) NOT NULL,Publisher VARCHAR(255) NOT NULL,Category VARCHAR(255) NOT NULL)";
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
     * @param conn Connection session
     * @param columnSql SQL for checking if the table has the column.
     * @param columnList List of columns.
     * @param bookSql SQL for adding books.
     * @param bookList List of books.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public void add(Connection conn,String columnSql,ArrayList<String> columnList,String bookSql,ArrayList<String> bookList) throws CollectionException
    {
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
        try
        {
            pstmt = conn.prepareStatement(columnSql);
            for (int i = 1; i < columnList.size(); i++)
            {
                pstmt.setString(1,table);
                pstmt.setString(i+1,columnList.get(i));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        //If not, add the column.
        try
        {
            if(pstmt.executeUpdate()<1)
            {
                String sql = "ALTER TABLE ? ADD ? VARCHAR(255) NOT NULL";
                for (int i = 1; i < columnList.size(); i++)
                {
                    try
                    {
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1,table);
                        pstmt.setString(2,columnList.get(i));
                        int update = pstmt.executeUpdate();
                        //Check if the test mode is on.
                        if(testMode){System.out.println("Ran columnSQL and updated: "+update+" rows");}
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                        exceptions.add(e);
                    }
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        //Add books to the table.
        try
        {
            pstmt = conn.prepareStatement(bookSql);
            for (int i = 1; i < bookList.size(); i++)
            {
                pstmt.setString(1,table);
                pstmt.setString(i+1,bookList.get(i));
            }
            int update = pstmt.executeUpdate();
            //Check if the test mode is on.
            if(testMode) {System.out.println("Ran bookSQL and updated: "+update+" rows");}
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        finally
        {
            //Close connection.
            DatabaseClose.close(pstmt,conn);
        }
        //Throw exceptions if any.
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
        //Start a connect session.
        conn = DatabaseConnect.connect();

        //Check if the table has the column Isbn.
        String columnSql = "SHOW COLUMNS FROM ? LIKE '?'";
        columnList.add("Issn");

        //Add books to the table.
        String bookSql = "INSERT INTO ? (Tittle,Author,Category,Isbn) VALUES (?,?,?,?)";
        for (BaseBooks baseBooks : booksInfo)
        {
            //Cast the object to Book, because we need the Isbn which only in book class.
            Book book = (Book) baseBooks;
            ArrayList<String> bookList = book.getBookInfo();
            //Call the add method.
            add(conn,columnSql,columnList,bookSql,bookList);
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
        //Start a connect session.
        conn = DatabaseConnect.connect();

        //Check if the table has the column Issn.
        String columnSql = "SHOW COLUMNS FROM ? LIKE '?'";
        columnList.add("Issn");

        //Add journals to the table.
        String journalSql = "INSERT INTO ? (Tittle,Author,Category,Issn) VALUES (?,?,?,?)";
        for (BaseBooks baseBooks : journalInfo)
        {
            Journal journal = (Journal) baseBooks;
            ArrayList<String> journalList = journal.getBookInfo();
            add(conn,columnSql,columnList,journalSql,journalList);
        }
    }

    /**
     * Add newspaper to database.
     * @param newspaperInfo List of newspapers.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public void addNewspaper(ArrayList<BaseBooks>newspaperInfo)throws CollectionException
    {
        ArrayList<String>columnList = new ArrayList<>();
        conn = DatabaseConnect.connect();

        //Check if the table has the column Issn and Copyright.
        String columnSql = "SHOW COLUMNS FROM ? LIKE '?' AND '?'";
        columnList.add("Issn");
        columnList.add("CopyRight");

        //Add books to the table.
        String journalSql = "INSERT INTO ? (Tittle,Author,Category,Issn,CopyRight) VALUES (?,?,?,?,?)";
        for (BaseBooks baseBooks : newspaperInfo)
        {
            Newspaper newspaper = (Newspaper) baseBooks;
            ArrayList<String> newspaperList = newspaper.getBookInfo();
            add(conn,columnSql,columnList,journalSql,newspaperList);
        }
    }
}