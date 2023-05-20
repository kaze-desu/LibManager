package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.BooksManageServiceImpl;
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