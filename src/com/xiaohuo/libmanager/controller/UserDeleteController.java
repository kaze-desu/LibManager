package com.xiaohuo.libmanager.controller;


import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.util.Scanner;

public class UserDeleteController {
    public boolean deleteUser(){
        String password;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your password: ");
        System.out.println();
        password = scanner.nextLine();
        System.out.println("Are you sure to delete your account");
        System.out.println("Y/N");
        String confirm = scanner.nextLine();
        if("Y".equals(confirm))
        {

        }
        return false;
    }
}


