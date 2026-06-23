package com.banking.api.automation;

import com.banking.api.automation.config.DatabaseConfig;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnectionTest {

    @Test
    public void testDBConnection() throws Exception {

        Connection conn = DatabaseConfig.getConnection();

        System.out.println("Connection Closed? : " + conn.isClosed());

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT 1");

        while (rs.next()) {
            System.out.println("Result = " + rs.getInt(1));
        }

        conn.close();
    }
}