package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BooksManageService;


import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.Map;

public class BookDeleteController {
    public void deleteBook() throws CollectionException
    {
        BooksManageService service = new BooksManageService();
        Map<Integer, ArrayList<String>> result;
        String name = "";
    }
}
