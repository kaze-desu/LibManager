package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BooksManagerService;
import com.xiaohuo.libmanager.services.template.TypeList;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * BookSearchController is the controller of searching books.
 * @author Xiaohuo(Wang Boyun)
 */
public class BookSearchController
{
    public void search() throws CollectionException
    {
        BooksManagerService service = new BooksManagerService();
        Map<Integer, ArrayList<String>> result;
        while (true)
        {
            System.out.println("Please choose a way to search your book:");
            System.out.println("1. Search by book type (Not recommended)");
            System.out.println("2. Search by book name");
            System.out.println("3. Search by book author");
            System.out.println("4. Search by book publisher");
            System.out.println("5. Search by book category");
            System.out.println("6. Search by book ISBN");
            System.out.println("7. Search by book ISSN");
            System.out.println("=================Please input your choice===================");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            switch (choice)
            {
                case "1":
                    searchByType();
                    break;
            }
        }

    }
    public void searchByType() throws CollectionException
    {
        BooksManagerService service = new BooksManagerService();
        Map<Integer, ArrayList<String>> result;
        result = service.searchByType(TypeList.BOOK.getType());
        int count = 0;
        for (Map.Entry<Integer,ArrayList<String>> information:result.entrySet())
        {
            int bookId = information.getKey();
            ArrayList<String> bookInfo = information.getValue();
            if(count %20==0 && count != 0)
            {
                System.out.println("Continue?");
                Scanner scanner = new Scanner(System.in);
                String confirm = scanner.nextLine();
                if ("N".equals(confirm) || "n".equals(confirm))
                {
                    break;
                }
            }
            else
            {
                for (String list:bookInfo)
                {
                    System.out.print(list + " ");
                }
            }

            System.out.println();
            count++;
        }
    }
}
