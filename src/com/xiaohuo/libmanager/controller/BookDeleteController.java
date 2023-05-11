package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.dao.BooksManageDao;
import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BooksManageServiceImpl;


import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BookDeleteController {
    public void deleteBook() throws CollectionException
    {
        BooksManageServiceImpl service = new BooksManageServiceImpl();
        System.out.print("Enter book title: ");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        Map<Integer,ArrayList<String>> bookList = service.search(title);
        if(bookList.size()==0){
            System.out.println("The book you want to delete is not found");
        }
        else {
            System.out.println(title);
            for(ArrayList<String> book: bookList.values()){
                System.out.println("ISBN="+book.get(4));
            }
            System.out.print("The one you want to delete is (enter the number start from 0): ");
            int choice = getUserInput(bookList.size());
            service.deleteBook(bookList.get(choice));
        }
    }
    public int getUserInput(int size){
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if(choice>=size || choice<0){
            System.out.println("Choice out of range, please enter again");
            return getUserInput(size);
        }
        else {
            System.out.println(choice);
            return choice;
        }
    }
}
