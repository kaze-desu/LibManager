package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.BooksManageServiceImpl;
import com.xiaohuo.libmanagergui.services.BooksStatusServiceImpl;
import com.xiaohuo.libmanagergui.services.template.TypeList;

import java.util.Scanner;


/**
 * @author xiaohuo(Wang Boyun)
 */
public class BookDeleteController {
    /**
     * Delete process:
     * Step 1 -> enter book type
     * Step 2 -> if user know the ISBN or ISSN, directly use the method deleteBookByIdentityCode() to delete
     * Step 3 -> if user don't know the ISBN or ISSN, firstly we use the title of the book to find all the
     *           ISBN or ISSN code of that book. Then we ask use to choose one ISBN or ISSN code and
     *           eventually use deleteBookByIdentityCode() to delete book
     */
    public void deleteBook() throws CollectionException
    {
        BooksManageServiceImpl serviceM = new BooksManageServiceImpl();
        BooksStatusServiceImpl serviceS = new BooksStatusServiceImpl();
        System.out.print("What type of book you want to delete: ");
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();

        while (!checkType(type)){
            System.out.println("Type not exist, please enter again");
            type = scanner.nextLine();
        }

/*        int choice;
        ArrayList<Integer> statusID;
        System.out.print("Enter 0 if you know the book ISBN or ISSN, other wise enter 1: ");
        choice = scanner.nextInt();
        scanner.nextLine();
        if(choice==0)
        {
            System.out.println("Enter ISBN or ISSN code: ");
            String code = scanner.nextLine();
            statusID = serviceS.getStatusId(serviceS.searchAllBookStatus(type,code));
            serviceM.deleteBookByIdentityCode(type,code,statusID);
        }
        else if(choice==1)
        {
            System.out.print("Enter the book title: ");
            String title = scanner.nextLine();
            ArrayList<String> book = serviceM.getBookByTitleAndType(title,type);
            statusID = serviceS.getStatusId(serviceS.searchAllBookStatus(type,book.get(4)));
            serviceM.deleteBookByIdentityCode(type,book.get(6),statusID);
        }*/
    }
    /**
     * Check is the user input type in the TypeList
     * @param type user input
     * @return true if user's input is correct (contained in the TypeList)
     */
    public boolean checkType(String type)
    {
        for(TypeList typeList: TypeList.values())
        {
            System.out.println(typeList.getType());
            if(typeList.getType().equals(type))
            {
                return true;
            }
        }
        return false;
    }
}
