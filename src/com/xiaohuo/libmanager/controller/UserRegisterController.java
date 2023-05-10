package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.services.UserManagerServiceImpl;

import java.util.Scanner;

/**
 * @author XU XIAO
 */
public class UserRegisterController
{

    public void register()
    {
        UserManagerServiceImpl services = new UserManagerServiceImpl();
        String name;
        String password;
        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please input your name and password:");
            System.out.println("Name:");
            name = scanner.nextLine();
            System.out.println("Password:");
            password = scanner.nextLine();
            System.out.println("Ensure your information:");
            System.out.println("Name:" + name);
            System.out.println("Password:" + password);
            System.out.println("Is this correct? (Y/N)");
            String confirm = scanner.nextLine();
            if ("Y".equals(confirm) || "y".equals(confirm))
            {
                if(!services.verifyNameExist(name))
                {
                    break;
                }
                else 
                {
                    System.out.println("The name is already exist, please try again.");
                }
            }
            services.addUser(name, password);

        }

    }

    /**
     * login into the e-library.
     */
    public void login()
    {
         String name;
         String password;
         Scanner scanner = new Scanner(System.in);
         System.out.println("name:");
         name = scanner.nextLine();
         System.out.println("password:");
         password = scanner.nextLine();
         System.out.println("name:" +name);
         System.out.println("password:"+password);
         String confirm = scanner.nextLine();
         if(!name.equals(confirm)&&password.equals(confirm))//e-library will not login
         {
/*             for(int i = 0;i <= 5;i++)
             {
                 System.out.println("You have no times to sign in");
                 break;
             }*/

         }
         else
         {
             System.out.println("welcome to the e-library world");
         }
    }
    public boolean logout()
    {
        System.out.println("Logout the system");
        return false;
    }
}

