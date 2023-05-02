package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.services.UserManagerService;

import java.util.Scanner;

/**
 * @author XU XIAO
 */
public class UserRegisterController
{

    public void register()
    {
        UserManagerService services = new UserManagerService();
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
    public void deleteUser(){
        String password;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your password: ");
        System.out.println();
        password = scanner.nextLine();
}
}
