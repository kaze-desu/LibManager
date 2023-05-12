package com.xiaohuo.libmanager.services;

import com.xiaohuo.libmanager.exception.CollectionException;
import org.junit.jupiter.api.Test;

class BooksStatusServiceImplTest
{

    @Test
    void addBookStatus() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        booksStatusServiceImpl.addBookStatus("Book","isbn1","A1",true);
    }

    @Test
    void checkBookType()
    {
    }

    @Test
    void searchBookStatus() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        System.out.println(booksStatusServiceImpl.searchBookStatus("Book","isbn1"));
    }
    @Test
    void editBookLocation() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        booksStatusServiceImpl.editBookLocation(1,"A3");
    }
    @Test
    void borrowBook() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        booksStatusServiceImpl.borrowBook(1);
    }
    @Test
    void revertBook() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        booksStatusServiceImpl.revertBook(1);
    }
    @Test
    void deleteBookStatus() throws CollectionException
    {
        BooksStatusServiceImpl booksStatusServiceImpl = new BooksStatusServiceImpl();
        booksStatusServiceImpl.deleteBookStatus(3);
    }
}