package com.xiaohuo.libmamnager.db;
import com.mysql.cj.jdbc.Driver;
/**
 * @author Wang Boyun
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

