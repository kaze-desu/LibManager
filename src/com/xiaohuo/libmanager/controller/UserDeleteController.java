package com.xiaohuo.libmanager.controller;

import java.util.Scanner;

public class UserDeleteController {
    public void deleteUser(){
        String password;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your password: ");
        System.out.println();
        password = scanner.nextLine();
    }
}
