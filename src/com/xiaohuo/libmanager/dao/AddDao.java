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
    public static final String COPY_RIGHT = "CopyRight";
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
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
     * @param bookList List of books.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public void add(String columnSql,ArrayList<String> columnList,String bookSql,ArrayList<String> bookList) throws CollectionException
    {
        //TODO 考虑如何使用JDBC Batch将语句一并提交到数据库，减少调用，增加效率
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
       //TODO 本处数量判断有问题，需要修改,，考虑通过检查，将不存在的列的位置(i)存储
        int count = 0;
        try
        {
            pstmt = conn.prepareStatement(columnSql);
            for (int i = 0; i < columnList.size(); i++)
            {
                pstmt.setString(1,columnList.get(i));
                pstmt.addBatch();
                if(testMode){System.out.println("try to check column: "+ columnList.get(i));}
            }
            int[] result = pstmt.executeBatch();

            //rs = pstmt.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }

        //If not, add the column.
        try
        {
            if(count<columnList.size())
            {
                for (int i = 0; i < columnList.size(); i++)
                {
                    String addSql = "ALTER TABLE "+ table +" ADD "+columnList.get(i)+" VARCHAR(255)";
                    try
                    {
                        pstmt = conn.prepareStatement(addSql);
                        pstmt.addBatch();
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                        exceptions.add(e);
                    }
                }
                int[] result = pstmt.executeBatch();
                //Check if the test mode is on.
                if(testMode){System.out.println("Ran columnSQL and updated: "+result.length+" rows");}
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

            for (int i = 0; i < bookList.size(); i++)
            {
                pstmt.setString(i+1,bookList.get(i));
                if(testMode){System.out.println("Adding bookList :"+bookList.get(i));}
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
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
        //Close connection.
        DatabaseClose.close(pstmt,conn);
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
        for (BaseBooks baseBooks : booksInfo)
        {
            //Cast the object to Book, because we need the Isbn which only in book class.
            Book book = (Book) baseBooks;
            ArrayList<String> bookList = book.getBookInfo();
            //Call the add method.
            add(columnSql,columnList,bookSql,bookList);
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
        for (BaseBooks baseBooks : journalInfo)
        {
            Journal journal = (Journal) baseBooks;
            ArrayList<String> journalList = journal.getBookInfo();
            add(columnSql,columnList,journalSql,journalList);
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
        columnList.add("COPY_RIGHT");

        //Add books to the table.
        String newspaperSql = "INSERT INTO "+table+" (Type,Tittle,Author,Publisher,Category,Issn,CopyRight) VALUES (?,?,?,?,?,?,?)";
        for (BaseBooks baseBooks : newspaperInfo)
        {
            Newspaper newspaper = (Newspaper) baseBooks;
            ArrayList<String> newspaperList = newspaper.getBookInfo();
            add(columnSql,columnList,newspaperSql,newspaperList);
        }
    }
}