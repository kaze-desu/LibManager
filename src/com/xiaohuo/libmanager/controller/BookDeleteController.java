package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BooksManageServiceImpl;
import com.xiaohuo.libmanager.services.template.TypeList;


import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class BookDeleteController {
    /**
     * Delete process:
     * Step 1 -> enter book type
     * Step 2 -> if user know the ISBN or ISSN, directly use the method deleteBookByIdentityCode() to delete
     * Step 3 -> if user don't know the ISBN or ISSN, firstly we use the title of the book to find all the
     *           ISBN or ISSN code of that book. Then we ask use to choose one ISBN or ISSN code and
     *           eventually use deleteBookByIdentityCode() to delete book
     * @throws CollectionException
     */
    public void deleteBook() throws CollectionException
    {
        BooksManageServiceImpl service = new BooksManageServiceImpl();
        System.out.println("What type of book you want to delete: ");
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        type.toLowerCase(Locale.ROOT);

        while (!checkType(type)){
            System.out.println("Type not exist, please enter again");
            type = scanner.nextLine();
        }

        int choice;
        System.out.print("Enter 0 if you know the book ISBN or ISSN, other wise enter 1: ");
        choice = scanner.nextInt();
        if(choice==0)
        {
            System.out.println("Enter ISBN or ISSN code: ");
            String code = scanner.toString();
            service.deleteBookByIdentityCode(type,code);
        }
        else if(choice==1)
        {
            System.out.print("Enter the book title: ");
            String title = scanner.nextLine();
            ArrayList<String> codeList = service.deleteBookBySearchTitle(title);
            int numCode = 0;
            for(String code: codeList)
            {
                System.out.println("Num "+numCode+" : "+code);
                numCode++;
            }
            System.out.print("The number of the code you want to delete is number: ");
            numCode = scanner.nextInt();
            service.deleteBookByIdentityCode(type,codeList.get(numCode));
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
