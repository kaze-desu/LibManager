package com.xiaohuo.libmamnager;

import com.xiaohuo.libmamnager.db.DatabaseConnectionImpl;
import java.util.*;
import java.io.*;

public class Init {
    private String username;
    private String password;
    private String address;

    public boolean checkConfig() throws FileNotFoundException {
/*        FileReader reader = new FileReader("db.properties");
        Properties properties = new Properties();
        properties.load(reader);*/
        //TODO Using function to identify properties exist or not, then return true or false
        return true;

    }
    public void createConfig() throws IOException {
        //TODO Using properties to create config file
    }
    public String getUsername() {
        //TODO Using properties to get username
        return username;
    }
    public String getPassword() {
        //TODO Using properties to get password
        return password;
    }
    public String getAddress() {
        //TODO Using properties to get address
        return address;
    }
}
