package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BooksManageServiceImpl;


import java.util.ArrayList;
import java.util.Map;

public class BookDeleteController {
    public void deleteBook() throws CollectionException
    {
        BooksManageServiceImpl service = new BooksManageServiceImpl();
        Map<Integer, ArrayList<String>> result;
        String name = "";
    }
}
