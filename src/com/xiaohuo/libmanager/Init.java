package com.xiaohuo.libmanager;

import java.util.*;
import java.io.*;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public class Init {
    private String username = "null";
    private String password = "null";
    private String address = "null";
    private final String rootPath = System.getProperty("user.dir")+"/src/com/xiaohuo/libmanager";
    private final String setting = rootPath+"/setting.properties";

    /**
     * The function use to initialize the config file.
     */
    public Init() throws IOException {
        while (true){
            try {
                if (checkConfig()){
                    System.out.println("Config file found");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please input your database username:");
            username = scanner.nextLine();
            System.out.println("Please input your database password:");
            password = scanner.nextLine();
            System.out.println("Please input your database address:");
            address = scanner.nextLine();
            System.out.println("Your database username is: "+username);
            System.out.println("Your database password is: "+password);
            System.out.println("Your database address is: "+address);
            System.out.println("Is this correct? (Y/N)");
            String confirm = scanner.nextLine();
            if ("Y".equals(confirm) || "y".equals(confirm)){
                try {
                    createConfig(username,password,address);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            else if ("N".equals(confirm) || "n".equals(confirm)){
                System.out.println("Please re-enter your database information");
            }
            else {
                System.out.println("Please input Y or N");
            }
        }
        readConfig();
    }

    /**
     *The function use to identify the config exist or not exist and return the status.
     * @return Identify config exist or not, and ensure the config content is correct.
     * @throws IOException Throw the error if reading or writing config met any wrong.
     */
    public boolean checkConfig() throws IOException {
        File file = new File(setting);
        //Create the file if it's not exist.
        if(!file.exists())
        {
            //Create file
            FileOutputStream createFile = new FileOutputStream(file);
            createFile.close();
        }
        //Read properties and check the data.
        Properties props = new Properties();
        props.load(new java.io.FileInputStream(setting));

        //Check each field exist or not.
        boolean configExist = false;
        if (props.getProperty("username") != null) {
            username = props.getProperty("username");
            configExist = true;
        }
        else
        {
            System.out.println("Config broken, username not found");
        }
        if (props.getProperty("password") != null) {
            password = props.getProperty("password");
        }
        else
        {
            System.out.println("Config broken, password not found");
            configExist = false;
        }
        if (props.getProperty("address") != null) {
            address = props.getProperty("address");
        }
        else
        {
            System.out.println("Config broken,address not found");
            configExist = false;
        }
        return configExist;

    }
    /**
     * The function use to create the config file.
     * @param username The username of database.
     * @param password The password of database.
     * @param address The address of database.
     * @throws IOException Throw the error if reading or writing config met any wrong.
     */
    public void createConfig(String username,String password,String address) throws IOException {
            Properties props = new Properties();
            props.setProperty("username", username);
            props.setProperty("password", password);
            props.setProperty("address", address);
            props.store(new FileOutputStream(setting), "Config file for library manager");
            System.out.println("Config file created");
    }

    public void readConfig() throws IOException {
        Properties props = new Properties();
        props.load(new java.io.FileInputStream(setting));
        username = props.getProperty("username");
        password = props.getProperty("password");
        address = props.getProperty("address");
    }
    public String getDatabaseUsername() {
        return username;
    }
    public String getDatabasePassword() {
        return password;
    }
    public String getDatabaseAddress() {
        return address;
    }
}
