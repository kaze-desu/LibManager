package com.xiaohuo.libmanager;

import com.xiaohuo.libmanager.exception.CollectionException;
/**
 * Main class
 * @author xiaohuo(Wang Boyun)
 */
public class Main
{
    public static void main(String[] args) throws CollectionException
    {
        System.out.println("Installed Check...");
        Init init = new Init();
        System.out.println("Installed Check Complete");
    }
}
