package com.xiaohuo.libmanager.services;


import com.xiaohuo.libmanager.dao.BooksManagerDao;
import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.template.Book;
import com.xiaohuo.libmanager.services.template.Journal;
import com.xiaohuo.libmanager.services.template.Newspaper;
import com.xiaohuo.libmanager.services.template.TypeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiaohuo.libmanager.dao.BooksManagerDao.BOOK_TABLE;
/**
 * This service use to accept data from controller, then classify and unzip the information.
 * ArrayList always appearing in this class, I hope someone could optimize it if there has a smarter way.
 * @author Xiaohuo (Wang Boyun)
 */
public class BooksManagerService
{
    BooksManagerDao dao = new BooksManagerDao();
    /**
     * The add function will transfer data to DAO, so it's not solving data, only transfer data.
     * @param booksInfo This param is a ArrayList that type of BaseBooks. For safety reason, I use a function (just like book.getXXX) to get unique value from each different extend class.
     *                 As you see, is not quite beautiful isn't it ?
     *                  4.29: Solved, so concision.
     */
    public void add(ArrayList<BaseBooks> booksInfo) throws CollectionException
    {
        dao.initTable();
        String type = booksInfo.get(0).type;
        //Identify the type of the book, then transfer the ArrayList object to DAO.

        if (TypeList.BOOK.toString().equals(type))
        {
            addBook(booksInfo);
        }
        else if (TypeList.JOURNAL.toString().equals(type))
        {
            addJournal(booksInfo);
        }
        else if(TypeList.NEWSPAPER.toString().equals(type))
        {
            addNewspaper(booksInfo);
        }
        else
        {
            throw new IllegalArgumentException("Invalid type, please check the type of the book including in template or not");
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
        String columnSql = "SHOW COLUMNS FROM "+BOOK_TABLE+" LIKE ?";
        columnList.add("Isbn");

        //Add books to the table.
        String bookSql = "INSERT INTO "+BOOK_TABLE+" (Type,Tittle,Author,Publisher,Category,Isbn) VALUES (?,?,?,?,?,?)";
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
        for (BaseBooks baseBooks : booksInfo)
        {
            //Cast the object to Book, because we need the Isbn which only in book class.
            Book book = (Book) baseBooks;
            ArrayList<String> bookList = book.getBookInfo();
            list.put(booksInfo.indexOf(book),bookList);
        }
        //Call the add method.
        dao.add(columnSql,columnList,bookSql,list);
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
        String columnSql = "SHOW COLUMNS FROM "+BOOK_TABLE+" LIKE ?";
        columnList.add("Issn");

        //Add journals to the table.
        String journalSql = "INSERT INTO "+BOOK_TABLE+" (Type,Tittle,Author,Publisher,Category,Issn) VALUES (?,?,?,?,?,?)";
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
        for (BaseBooks baseBooks : journalInfo)
        {
            Journal journal = (Journal) baseBooks;
            ArrayList<String> bookList = journal.getBookInfo();
            list.put(journalInfo.indexOf(journal),bookList);
            //Call the add method.
            dao.add(columnSql,columnList,journalSql,list);
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
        String columnSql = "SHOW COLUMNS FROM "+BOOK_TABLE+" LIKE ?";
        columnList.add("Issn");
        columnList.add("CopyRight");

        //Add books to the table.
        String newspaperSql = "INSERT INTO "+BOOK_TABLE+" (Type,Tittle,Author,Publisher,Category,Issn,CopyRight) VALUES (?,?,?,?,?,?,?)";
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
        for (BaseBooks baseBooks : newspaperInfo)
        {
            Newspaper newspaper = (Newspaper) baseBooks;
            ArrayList<String> newspaperList = newspaper.getBookInfo();
            list.put(newspaperInfo.indexOf(newspaper),newspaperList);
            //Call the add method.
            dao.add(columnSql,columnList,newspaperSql,list);
        }
    }

    /*
      The search function will transfer data to DAO, so it's not solving data, only transfer data.
      Search function use to search books by different kinds of ways and return a list.
     */

    /**Search the book by using type.
     * @param type The type of the book.
     * @return A list of books.
     */
    public Map<Integer, ArrayList<String>>searchByType(String type)
    {
        List<Throwable> exceptions = new ArrayList<>();
        int size = 0;
        if(TypeList.BOOK.getType().equals(type))
        {
            try
            {
                dao.search("Type",TypeList.BOOK.getType(),TypeList.BOOK.elements());
            }
            catch (CollectionException e)
            {
                exceptions.add(e);
            }
        }
        else if(TypeList.JOURNAL.getType().equals(type))
        {
            try
            {
                dao.search("Type",TypeList.JOURNAL.getType(),TypeList.JOURNAL.elements());
            }
            catch (CollectionException e)
            {
                exceptions.add(e);
            }
        }
        else if(TypeList.NEWSPAPER.getType().equals(type))
        {
            try
            {
                dao.search("Type",TypeList.NEWSPAPER.getType(),TypeList.NEWSPAPER.elements());
            }
            catch (CollectionException e)
            {
                exceptions.add(e);
            }
        }
        else
        {
            throw new IllegalArgumentException("Invalid type, please check the type of the book including in template or not");
        }


        return null;
    }
    public Map<Integer, ArrayList<String>>searchByTittle(String tittle)
    {

        return null;
    }
    public Map<Integer, ArrayList<String>>searchByAuthor(String author)
    {

        return null;
    }
    public Map<Integer, ArrayList<String>>searchByPublisher(String publisher)
    {

        return null;
    }
    public Map<Integer, ArrayList<String>>searchByCategory(String category)
    {

        return null;
    }
}
