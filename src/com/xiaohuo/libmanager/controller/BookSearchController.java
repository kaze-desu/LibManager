package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BooksManageService;
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
    public int search() throws CollectionException
    {
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
            int bookId;
            switch (choice)
            {
                case "1" ->
                {
                    bookId = searchByType();
                    return bookId;
                }
                case "2" ->
                {
                    bookId = searchByName();
                    return bookId;
                }
                case "3" ->
                {
                    bookId = searchByAuthor();
                    return bookId;
                }
                case "4" ->
                {
                    bookId = searchByPublisher();
                    return bookId;
                }
                case "5" ->
                {
                    bookId = searchByCategory();
                    return bookId;
                }
                case "6" ->
                {
                    bookId = searchByIsbn();
                    return bookId;
                }
                case "7" ->
                {
                    bookId = searchByIssn();
                    return bookId;
                }
                default ->
                {
                }
            }
        }

    }
    public int searchByType() throws CollectionException
    {
        BooksManageService service = new BooksManageService();
        Map<Integer, ArrayList<String>> result;
        result = service.searchByType(TypeList.BOOK.getType());
        int count = 0;
        if (result.size() == 0)
        {
            System.out.println("No result found.");
            return -1;
        }
        else
        {
            for (Map.Entry<Integer,ArrayList<String>> information:result.entrySet())
            {
                int bookId = information.getKey();
                ArrayList<String> bookInfo = information.getValue();
                if(count %20==0 && count != 0|| count == result.size())
                {
                    System.out.println("Continue?");
                    Scanner scanner = new Scanner(System.in);
                    String confirm = scanner.nextLine();
                    if ("N".equals(confirm) || "n".equals(confirm))
                    {
                        System.out.println("Choice your book by inter id:");
                        bookId = scanner.nextInt();
                        return bookId;
                    }
                }
                else
                {
                    System.out.print("Book id: "+bookId);
                    for (String list:bookInfo)
                    {
                        System.out.print(list + " ");
                    }
                }
                System.out.println();
                count++;
            }
        }
        return -1;
    }
    public int searchByName() throws CollectionException
    {
        return -1;
    }
    public int searchByAuthor() throws CollectionException
    {
        return -1;
    }
    public int searchByPublisher() throws CollectionException
    {
        return -1;
    }
    public int searchByCategory() throws CollectionException
    {
        return -1;
    }
    public int searchByIsbn() throws CollectionException
    {
        return -1;
    }
    public int searchByIssn() throws CollectionException
    {
        return -1;
    }
}
