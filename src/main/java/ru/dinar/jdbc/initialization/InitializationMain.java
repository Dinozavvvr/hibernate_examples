package ru.dinar.jdbc.initialization;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class InitializationMain {

    public static final String DB_URL = "jdbc:postgresql://localhost:5432/hibernate_demo";

    public static void main(String[] args) {
        // 1 - checking that drivers are loaded from classpath automatically
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        if (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            // expecting class org.postgresql.Driver
            System.out.println(driver.getClass());
        }

        // 2 - getting postgresql driver directly from DriverManager by connection url
        try {
            org.postgresql.Driver postgresqlDriver = (org.postgresql.Driver) DriverManager.getDriver(DB_URL);
            // expecting that driver is not empty
            System.out.println(postgresqlDriver != null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
