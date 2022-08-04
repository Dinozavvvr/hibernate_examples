package ru.dinar.inheritance;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateConfig {

    public static ServiceRegistry serviceRegistry() {
        return new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect")
                .applySetting("hibernate.connection.url", "jdbc:postgresql://localhost:5432/hibernate_demo")
                .applySetting("hibernate.connection.username", "postgres")
                .applySetting("hibernate.connection.password", "postgres")
                .applySetting("hibernate.use_sql_comments", "true")
                .applySetting("hibernate.format_sql", "true")
                .applySetting("hibernate.hbm2ddl.auto", "create-drop")
                .build();
    }

}
