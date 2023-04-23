package com.xiaohuo.libmanager;

import com.xiaohuo.libmanager.Exception.CustomException;
/**
 * Main class
 * @author xiaohuo(Wang Boyun)
 */
public class Main
{
    public static void main(String[] args) throws CustomException
    {
        System.out.println("Installed Check...");
        Init init = new Init();
        System.out.println("Installed Check Complete");
    }
}
