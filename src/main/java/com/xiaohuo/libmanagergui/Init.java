package com.xiaohuo.libmanagergui;

import com.xiaohuo.libmanagergui.db.DatabaseConnect;
import com.xiaohuo.libmanagergui.exception.CollectionException;

import java.util.*;
import java.io.*;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public class Init
{
    private String username = "null";
    private String password = "null";
    private String address = "null";
    private String databaseName = "null";
    private String testMode = "false";
    private final String rootPath = System.getProperty("user.dir") + "/src/com/xiaohuo/libmanager";
    private final String setting = rootPath + "/setting.properties";
    /**
     * The function use to initialize the config file.
     */
    public Init() throws CollectionException
    {
        List<Exception> exceptions = new ArrayList<>();
        while (true)
        {
            //Check if the config file is exist.
            if (checkConfig())
            {
                break;
            }
            //If the config file is not exist, create a new one.
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please input your database address:");
            address = scanner.nextLine();
            System.out.println("Please input your database username:");
            username = scanner.nextLine();
            System.out.println("Please input your database password:");
            password = scanner.nextLine();
            System.out.println("Please input your database name:");
            databaseName = scanner.nextLine();
            System.out.println("Your database address is: " + address);
            System.out.println("Your database username is: " + username);
            System.out.println("Your database password is: " + password);
            System.out.println("Your database name is: " + databaseName);
            System.out.println("Is this correct? (Y/N)");
            String confirm = scanner.nextLine();
            if ("Y".equals(confirm) || "y".equals(confirm))
            {
                try
                {
                    //Test the database connection, if failed, return to the beginning.
                    if(!DatabaseConnect.check(username, password, address, databaseName))
                    {
                        System.out.println("Connect test failed, please check your data or or internet status.");
                        continue;
                    }
                    System.out.println("Connect test success, creating config file...");
                    createConfig();
                }
                catch (IOException e)
                {
                    exceptions.add(e);
                }
                break;
            }
            else if ("N".equals(confirm) || "n".equals(confirm))
            {
                System.out.println("Please re-enter your database information");
            }
            else
            {
                System.out.println("Please input Y or N");
            }
        }
        readConfig();
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }

    /**
     *The function use to identify the config exist or not exist and return the status.
     * @return Identify config exist or not, and ensure the config content is correct.
     */
    public boolean checkConfig() throws CollectionException
    {
        List<Throwable>exceptions = new ArrayList<>();
        File file = new File(setting);
        //Create the file if it's not exist.
        if(!file.exists())
        {
            //Create file
            FileOutputStream createFile = null;
            try
            {
                createFile = new FileOutputStream(file);
            } catch (FileNotFoundException e)
            {
                exceptions.add(e);
            }
            try
            {
                assert createFile != null;
                createFile.close();
            }
            catch (IOException e)
            {
                exceptions.add(e);
            }
        }
        //Read properties and check the data.
        Properties props = new Properties();
        try
        {
            props.load(new FileInputStream(setting));
        }
        catch (IOException e)
        {
            exceptions.add(e);
        }
        //Check each field exist or not.
        boolean configExist = false;
        String username = "username";
        String password = "password";
        String address = "address";
        String databaseName = "databaseName";
        String testMode = "testMode";
        if (props.getProperty(username) != null)
        {
            this.username = props.getProperty("username");
            configExist = true;
        }
        else
        {
            System.out.println("Config broken, username not found");
        }
        if (props.getProperty(password) != null)
        {
            this.password = props.getProperty("password");
        }
        else
        {
            System.out.println("Config broken, password not found");
            configExist = false;
        }
        if (props.getProperty(address) != null)
        {
            this.address = props.getProperty("address");
        }
        else
        {
            System.out.println("Config broken,address not found");
            configExist = false;
        }
        if (props.getProperty(databaseName) != null)
        {
            this.databaseName = props.getProperty("databaseName");
        }
        else
        {
            System.out.println("Config broken,databaseName not found");
            configExist = false;
        }
        if (props.getProperty(testMode) != null)
        {
            this.testMode = props.getProperty("testMode");
        }
        else
        {
            System.out.println("Config broken,testMode not found");
            configExist = false;
        }
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
        return configExist;
    }
    /**
     * The function use to create the config file.
     * @throws IOException Throw the error if reading or writing config met any wrong.
     */
    public void createConfig() throws IOException
    {
            Properties props = new Properties();
            props.setProperty("username", username);
            props.setProperty("password", password);
            props.setProperty("address", address);
            props.setProperty("databaseName", databaseName);
            props.setProperty("testMode", testMode);
            props.store(new FileOutputStream(setting), "Config file for library manager");
            System.out.println("Config file created");
    }

    public void readConfig() throws CollectionException
    {
        List<Throwable>exceptions = new ArrayList<>();
        Properties props = new Properties();
        try
        {
            props.load(new FileInputStream(setting));
        }
        catch (IOException e)
        {
            exceptions.add(e);
        }
        username = props.getProperty("username");
        password = props.getProperty("password");
        address = props.getProperty("address");
        databaseName = props.getProperty("databaseName");
        testMode = props.getProperty("testMode");
        if (exceptions.size() > 0)
        {
            throw new CollectionException(exceptions);
        }
    }
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
    public String getAddress()
    {
        return address;
    }
    public String getDatabaseName()
    {
        return databaseName;
    }
    public boolean getTestMode()
    {
        String testMode = "true";
        return this.testMode.equals(testMode);
    }
}
