package com.vishu.project.corebank.util;

import java.sql.*;

public class DatabaseConnection {
    /*
import package
load and register
create connection //done till here
create statement
execute statement
process the results
close


*/
    private static final String URL = "jdbc:postgresql://localhost:5432/corebank_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1207"; // whatever you set during install

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }

}
