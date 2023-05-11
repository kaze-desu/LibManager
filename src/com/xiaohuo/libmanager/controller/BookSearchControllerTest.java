package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BooksManageServiceImpl;
import org.junit.jupiter.api.Test;

class BookSearchControllerTest
{

    @Test
    void search() throws CollectionException
    {
        BooksManageServiceImpl service = new BooksManageServiceImpl();
        System.out.println(service.search("tittle1"));
    }
}