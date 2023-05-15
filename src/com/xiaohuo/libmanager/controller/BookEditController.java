package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BooksManageServiceImpl;
import com.xiaohuo.libmanager.services.template.TypeList;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class BookEditController
{
    public void editBook() throws CollectionException
    {
        BooksManageServiceImpl service = new BooksManageServiceImpl();
        Scanner scanner = new Scanner(System.in);

        System.out.println("What type of book you want to edit: ");
        String type = scanner.nextLine();
        type.toLowerCase(Locale.ROOT);

        while (!checkType(type)){
            System.out.println("Type not exist, please enter again");
            type = scanner.nextLine();
        }

        System.out.print("Enter the column you want to edit: ");
        String column = scanner.nextLine();

        System.out.print("Enter the content you want to change: ");
        String content = scanner.nextLine();

        System.out.print("Enter 0 if you know the book ISBN or ISSN, other wise enter 1: ");
        int choice;
        choice = scanner.nextInt();

        if(choice==0)
        {
            System.out.println("Enter ISBN or ISSN code: ");
            String code = scanner.toString();
            service.editBookInformation(column,content,type,code);
        }
        else if(choice==1)
        {
            System.out.print("Enter the book title: ");
            String title = scanner.nextLine();

            ArrayList<String> codeList = service.getBookCodeListBySearchTitle(title);
            int numCode = 0;
            for(String code: codeList)
            {
                System.out.println("Num "+numCode+" : "+code);
                numCode++;
            }

            System.out.print("The number of the code you want to edit is number: ");
            numCode = scanner.nextInt();

            service.editBookInformation(column,content,type, codeList.get(numCode));
        }
    }

    /**
     * Check is the user input type in the TypeList
     * @param type
     * @return true if user's input is correct (contained in the TypeList)
     */
    public boolean checkType(String type)
    {
        for(TypeList typeList: TypeList.values())
        {
            if(typeList.getType().toLowerCase(Locale.ROOT)==type.toLowerCase(Locale.ROOT))
            {
                return true;
            }
        }
        return false;
    }
}

