package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.services.BooksManagerService;

public class BookSearchController
{
    public void search()
    {
        BooksManagerService service = new BooksManagerService();
        while (true)
        {
            System.out.println("Please choose a way to search your book:");
            System.out.println("1. Search by book type (Not recommended)");
            System.out.println("2. Search by book name");
            System.out.println("3. Search by book author");
            System.out.println("4. Search by book publisher");
            System.out.println("5. Search by book ISBN");
            //service.search("test")
        }
    }
}
