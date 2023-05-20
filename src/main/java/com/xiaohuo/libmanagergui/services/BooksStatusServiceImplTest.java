package com.xiaohuo.libmanagergui.services;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import org.junit.Test;


/**
 * @author Xiaohuo(Wang Boyun)
 */
public class BooksStatusServiceImplTest
{

    @Test
    public void addBookStatus() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        booksStatusServiceImpl.addBookStatus("Book","isbn1","A1",true);
    }

    @Test
    public void checkBookType()
    {
    }

    @Test
    public void searchBookStatus() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        System.out.println(booksStatusServiceImpl.searchBookStatus("Book","isbn1"));
    }
    @Test
    public void editBookLocation() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        booksStatusServiceImpl.editBookLocation(1,"A3");
    }
    @Test
    public void borrowBook() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        booksStatusServiceImpl.borrowBook(1);
    }
    @Test
    public void revertBook() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        booksStatusServiceImpl.revertBook(1);
    }
    @Test
    public void deleteBookStatus() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        booksStatusServiceImpl.deleteBookStatus(3);
    }
}