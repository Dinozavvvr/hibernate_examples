package ru.dinar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import ru.dinar.model.Country;
import ru.dinar.model.Item;
import ru.dinar.model.User;
import ru.dinar.model.UserId;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    // set jdk logging level to DEBUG(FINE) pragmatically or via logging.properties
    static {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.FINE);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.FINE);
        }
    }

    public static void main(String[] args) {
        ServiceRegistry serviceRegistry = HibernateConfig.serviceRegistry();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        metadataSources.addAnnotatedClass(User.class);
        metadataSources.addAnnotatedClass(Country.class);
        metadataSources.addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = metadataSources.buildMetadata().buildSessionFactory();


        Session session = sessionFactory.openSession();

        // begin transaction
        session.getTransaction().begin();

        // composite primary key
        Country country = new Country();
        country.setTitle("Russia");
        session.save(country);

        UserId userId = new UserId("admin", "+79200202202", country.getId());
        User user = new User(userId);
        session.persist(user);

        // composite foreign key
        Item item = new Item();
        item.setOwner(user);
        session.persist(item);

        // проблема с подгрузкой User.country. правильный селект но на уровне java классов объект null
        User persistUser = session.find(User.class, userId);
        System.out.println(persistUser.toString());

        // это не null
        System.out.println(session.find(Country.class, country.getId()).toString());

        List<Item> items = (List<Item>) session.createQuery("select i from Item i where i.owner = :user")
                .setParameter("user", persistUser)
                .list();

        Item persistItem = items.get(0);
        System.out.println(persistItem.toString());

        session.getTransaction().commit();
    }

}