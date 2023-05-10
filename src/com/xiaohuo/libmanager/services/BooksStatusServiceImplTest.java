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
}