package com.xiaohuo.libmanager.db;
import com.mysql.cj.jdbc.Driver;
/**
 * @author Xiaohuo(Wang Boyun)
 */
public class DatabaseConnectionImpl implements DatabaseConnection {

    private String address = "localhost";
    private String username = "root";
    private String password = "null";
    private String URL = "jdbc:mysql://"+address+"/useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";


    @Override
    public void connect() {


    }
}

