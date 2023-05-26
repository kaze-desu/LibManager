package com.xiaohuo.libmanagergui.services;


import com.xiaohuo.libmanagergui.dao.BooksManageDao;
import com.xiaohuo.libmanagergui.dao.BooksStatusDao;
import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.template.Book;
import com.xiaohuo.libmanagergui.services.template.Journal;
import com.xiaohuo.libmanagergui.services.template.Newspaper;
import com.xiaohuo.libmanagergui.services.template.TypeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiaohuo.libmanagergui.dao.BooksManageDao.BOOK_TABLE;
/**
 * This service use to accept data from controller, then classify and unzip the information.
 * ArrayList always appearing in this class, I hope someone could optimize it if there has a smarter way.
 * @author Xiaohuo (Wang Boyun)
 */
public class BooksManageServiceImpl implements BooksManageService
{
    @Override
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

    @Override
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
            try
            {
                //Check if the Isbn is exists.
                if(getBookId(TypeList.BOOK.getType(), book.getIsbn())!=-1)
                {
                    //TODO 考虑如何传递给Controller
                    System.out.println("The book is already exist!");
                    continue;
                }
            }
            catch(CollectionException ignored)
            {
            }
            ArrayList<String> bookList = book.getBookInfo();
            list.put(booksInfo.indexOf(book),bookList);
        }
        //Call the add method.
        dao.addBook(columnList,bookSql,list);
    }

    @Override
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
            try
            {
                //Check if the Isbn is exists.
                if(getBookId(TypeList.JOURNAL.getType(), journal.getIssn())!=-1)
                {
                    System.out.println("The book is already exist!");
                    continue;
                }
            }
            catch(CollectionException ignored)
            {
            }
            ArrayList<String> bookList = journal.getBookInfo();
            list.put(journalInfo.indexOf(journal),bookList);
        }
        //Call the add method.
        dao.addBook(columnList,journalSql,list);


    }

    @Override
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
            try
            {
                //Check if the Isbn is exists.
                if(getBookId(TypeList.NEWSPAPER.getType(), newspaper.getIssn())!=-1)
                {
                    System.out.println("The book is already exist!");
                    continue;
                }
            }
            catch(CollectionException ignored)
            {
            }
            list.put(newspaperInfo.indexOf(newspaper),newspaperList);
        }
        //Call the add method.
        dao.addBook(columnList,newspaperSql,list);

    }

    /*
      The search function will transfer data to DAO, so it's not solving data, only transfer data.
      Search function use to search books by different kinds of ways and return a list.
     */

    @Override
    public Map<Integer, ArrayList<String>>search(String value) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        List<Throwable> exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>>bookList = new HashMap<>();
        try
        {
            bookList = dao.searchBook(value);
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

    @Override
    public Map<Integer, ArrayList<String>>searchByType(String type) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        List<Throwable> exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>>bookList = new HashMap<>();
        try
        {
            bookList = dao.searchBook("Type",type);
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

    @Override
    public Map<Integer, ArrayList<String>>searchByAuthor(String author) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        List<Throwable> exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>>bookList = new HashMap<>();
        try
        {
            bookList = dao.searchBook("Author",author);
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

    @Override
    public Map<Integer, ArrayList<String>>searchByPublisher(String publisher) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        List<Throwable> exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>>bookList = new HashMap<>();
        try
        {
            bookList = dao.searchBook("Publisher",publisher);
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

    @Override
    public Map<Integer, ArrayList<String>>searchByCategory(String category) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        List<Throwable> exceptions = new ArrayList<>();
        Map<Integer,ArrayList<String>>bookList = new HashMap<>();
        try
        {
            bookList = dao.searchBook("Category",category);
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

    @Override
    public ArrayList<String> searchByIdentityCode(String identityType,String identityCode) throws CollectionException
    {
        var dao = new BooksManageDao();
        //Because the identityCode is an only value of the book, so the for loop should only run once.
        if (identityType.equals(TypeList.BOOK.getType()))
        {
            var list = dao.searchBook("Isbn",identityCode);
            for (Integer key:list.keySet())
            {
                return list.get(key);
            }
        }
        else
        {
            var list = dao.searchBook("Issn",identityCode);
            for (Integer key:list.keySet())
            {
                return list.get(key);
            }
        }
        return null;
    }

    @Override
    public int getBookId(String identityType, String identityCode) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        Map<Integer,ArrayList<String>>bookList;
        int bookId = 0;
        bookList = dao.searchBook("Type",identityType);

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
     * If the user don't know the ISBN OR ISSN of the book they want to delete, this method will search the
     * book first and return a list contain all the ISSN or ISBN of the target book
     * @param title the title of the book
     * @return a list contain all the ISSN or ISBN of the target book
     * @throws CollectionException if the book is not exist
     */
    @Override
    public ArrayList<String> getBookByTitleAndType(String title, String type) throws CollectionException
    {
        ArrayList<String> book = new ArrayList<>();
        Map<Integer, ArrayList<String>> result;
        result = search(title);

        for (Map.Entry<Integer, ArrayList<String>> entry : result.entrySet())
        {
            ArrayList<String> bookInfo = entry.getValue();
            if(bookInfo.get(0).equals(type))
            {
                return bookInfo;
            }
        }
        return book;
    }

    @Override
    public void deleteBookByIdentityCode(String type,String code, ArrayList<Integer> statusID) throws CollectionException
    {
        BooksManageDao daoM = new BooksManageDao();
        BooksStatusDao daoS = new BooksStatusDao();
        List<Throwable> exceptions = new ArrayList<>();

        try
        {
            int bookID = getBookId(type,code);
            System.out.print("the id is: "+bookID);
            daoM.delete(bookID);
            if(statusID!=null)
            {
                for(int ID: statusID)
                {
                    daoS.deleteStatus(ID);
                }
            }
        }catch (CollectionException e)
        {
            exceptions.add(e);
        }
        if(exceptions.size()>0){
            throw new CollectionException(exceptions);
        }
    }

    @Override
    public void editBookInformation(String column,String targetContent,String type,String code) throws CollectionException
    {
        BooksManageDao dao = new BooksManageDao();
        List<Throwable> exceptions = new ArrayList<>();

        try {
            int ID = getBookId(type, code);
            dao.editBook(ID,column,targetContent);
        }catch (CollectionException e)
        {
            exceptions.add(e);
        }
        if (exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }
    }
}
