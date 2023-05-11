package com.xiaohuo.libmanager.services;


import com.xiaohuo.libmanager.dao.BooksManageDao;
import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.template.Book;
import com.xiaohuo.libmanager.services.template.Journal;
import com.xiaohuo.libmanager.services.template.Newspaper;
import com.xiaohuo.libmanager.services.template.TypeList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiaohuo.libmanager.dao.BooksManageDao.BOOK_TABLE;
/**
 * This service use to accept data from controller, then classify and unzip the information.
 * ArrayList always appearing in this class, I hope someone could optimize it if there has a smarter way.
 * @author Xiaohuo (Wang Boyun)
 */
public class BooksManageService
{


    /**
     * The add function will transfer data to DAO, so it's not solving data, only transfer data.
     * @param booksInfo This param is a ArrayList that type of BaseBooks. For safety reason, I use a function (just like book.getXXX) to get unique value from each different extend class.
     *                 As you see, is not quite beautiful isn't it ?
     *                  4.29: Solved, so concision.
     */
    public void add(ArrayList<BaseBooks> booksInfo) throws CollectionException
    {
        String type = booksInfo.get(0).type;
        //Identify the type of the book, then transfer the ArrayList object to DAO.

        if (TypeList.BOOK.getType().equals(type))
        {
            addBook(booksInfo);
        }
        else if (TypeList.JOURNAL.getType().equals(type))
        {
            addJournal(booksInfo);
        }
        else if(TypeList.NEWSPAPER.getType().equals(type))
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
        BooksManageDao dao = new BooksManageDao();
        ArrayList<String>columnList = new ArrayList<>();
        //Check if the table has the column Isbn.
        columnList.add("Isbn");

        //Add books to the table.
        String bookSql = "INSERT INTO %s (Type,Tittle,Author,Publisher,Category,Isbn) VALUES ";
        bookSql = String.format(bookSql,BOOK_TABLE);
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
        for (BaseBooks baseBooks : booksInfo)
        {
            //Cast the object to Book, because we need the Isbn which only in book class.
            Book book = (Book) baseBooks;
            ArrayList<String> bookList = book.getBookInfo();
            list.put(booksInfo.indexOf(book),bookList);
        }
        //Call the add method.
        dao.add(columnList,bookSql,list);
    }

    /**
     * Add journal to database.
     * @Type: Journal
     * @param journalInfo List of journals.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public void addJournal(ArrayList<BaseBooks>journalInfo)throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        ArrayList<String>columnList = new ArrayList<>();
        //Check if the table has the column Issn.
        columnList.add("Issn");

        //Add journals to the table.
        String journalSql = "INSERT INTO %s (Type,Tittle,Author,Publisher,Category,Issn) VALUES ";
        journalSql = String.format(journalSql,BOOK_TABLE);
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
        for (BaseBooks baseBooks : journalInfo)
        {
            Journal journal = (Journal) baseBooks;
            ArrayList<String> bookList = journal.getBookInfo();
            list.put(journalInfo.indexOf(journal),bookList);
        }
        //Call the add method.
        dao.add(columnList,journalSql,list);


    }

    /**
     * Add newspaper to database.
     * @Type: Newspaper
     * @param newspaperInfo List of newspapers.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public void addNewspaper(ArrayList<BaseBooks>newspaperInfo)throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        ArrayList<String>columnList = new ArrayList<>();
        //Check if the table has the column Issn and Copyright.
        columnList.add("Issn");
        columnList.add("CopyRight");

        //Add books to the table.
        String newspaperSql = "INSERT INTO %s (Type,Tittle,Author,Publisher,Category,Issn,CopyRight) VALUES ";
        newspaperSql = String.format(newspaperSql,BOOK_TABLE);
        Map<Integer,ArrayList<String>> list = new HashMap<>(1000);
        for (BaseBooks baseBooks : newspaperInfo)
        {
            Newspaper newspaper = (Newspaper) baseBooks;
            ArrayList<String> newspaperList = newspaper.getBookInfo();
            list.put(newspaperInfo.indexOf(newspaper),newspaperList);
        }
        //Call the add method.
        dao.add(columnList,newspaperSql,list);

    }

    /*
      The search function will transfer data to DAO, so it's not solving data, only transfer data.
      Search function use to search books by different kinds of ways and return a list.
     */


    /**
     * Search the book by general search mod.
     * @return A list of books filtered by tittle.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public Map<Integer, ArrayList<String>>search(String value) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        List<Throwable> exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>>bookList = new HashMap<>();
        try
        {
            bookList = dao.search(value);
        } catch (CollectionException e)
        {
            exceptions.add(e);
        }
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
        return bookList;
    }

    /*
    Advanced search function.
     */

    /**Search by type.
     * @param type The type of the book.
     * @return A list of books filtered by type.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public Map<Integer, ArrayList<String>>searchByType(String type) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        List<Throwable> exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>>bookList = new HashMap<>();
        try
        {
            bookList = dao.search("Type",type);
        } catch (CollectionException e)
        {
            exceptions.add(e);
        }
        if (exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
        return bookList;
    }

    /**
     * Search by author.
     * @param author The author of the book.
     * @return A list of books filtered by author.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public Map<Integer, ArrayList<String>>searchByAuthor(String author) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        List<Throwable> exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>>bookList = new HashMap<>();
        try
        {
            bookList = dao.search("Author",author);
        } catch (CollectionException e)
        {
            exceptions.add(e);
        }
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
        return bookList;
    }

    /**
     * Search by publisher.
     * @param publisher The publisher of the book.
     * @return A list of books filtered by publisher.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public Map<Integer, ArrayList<String>>searchByPublisher(String publisher) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        List<Throwable> exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>>bookList = new HashMap<>();
        try
        {
            bookList = dao.search("Publisher",publisher);
        } catch (CollectionException e)
        {
            exceptions.add(e);
        }
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
        return bookList;
    }

    /**
     *  Search by category.
     * @param category The category of the book.
     * @return A list of books filtered by category.
     * @throws CollectionException Exception thrown when there is any error.
     */
    public Map<Integer, ArrayList<String>>searchByCategory(String category) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        List<Throwable> exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>>bookList = new HashMap<>();
        try
        {
            bookList = dao.search("Category",category);
        } catch (CollectionException e)
        {
            exceptions.add(e);
        }
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
        return bookList;
    }

    /**
     * Search by Isbn/Issn and return a bookID, which helpful for Status table to bind with book.
     * @param identityType Choose Isbn/Issn
     * @param identityCode The code of Isbn/Issn
     * @return The bookID
     * @throws CollectionException Exception thrown when there is any error.
     */
    public int getBookID(String identityType,String identityCode) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        Map<Integer,ArrayList<String>>bookList;
        int bookId = 0;
        bookList = dao.search("Type",identityType);

        if(bookList.size()>0)
        {
            for (Map.Entry<Integer, ArrayList<String>> entry : bookList.entrySet())
            {
                ArrayList<String> bookInfo = entry.getValue();
                if(bookInfo.get(5).equals(identityCode))
                {
                    bookId = entry.getKey();
                }
            }
        }
        else
        {
            return -1;
        }
        return bookId;
    }

    /**
     * Delete book in the database according to their title
     * @param book the book the user want to delete
     */
    public void deleteBook(ArrayList<String> book) throws CollectionException{
        List<Throwable> exceptions = new ArrayList<>();
        BooksManageDao dao = new BooksManageDao();
        String code = book.get(4);
        String type = book.get(3);
        int ID = getBookID(type,code);
        try
        {
            dao.delete(ID);
        }
        catch (CollectionException e)
        {
            exceptions.add(e);
        }
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
    }

    public void editBookTitle() throws CollectionException{}

    public void editBookAuthor() throws CollectionException{}


}
