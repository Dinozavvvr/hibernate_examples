package ru.dinar;

import ru.dinar.model.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class HelloWorldJpa {

    // set jdk logging level to DEBUG(FINE) pragmatically or via logging.properties
    static {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.FINE);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.FINE);
        }
    }

    // hello world Hibernate Example
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("HelloWorld");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        // beginning new transaction
        transaction.begin();

        Message message = new Message();
        message.setText("Hello world");

        entityManager.persist(message);

        // here is JSQL not SQL
        List<Message> messages = entityManager.createQuery("select m from Message m").getResultList();
        Message persistMessage = messages.get(0);
        persistMessage.setText("Updated Hello world");

        // committing new transaction
        transaction.commit();

        entityManager.close();
    }

}
