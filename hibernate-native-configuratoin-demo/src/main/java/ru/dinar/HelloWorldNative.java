package ru.dinar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import ru.dinar.model.Message;
import ru.dinar.model.Message_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@SuppressWarnings("unchecked")
public class HelloWorldNative {

    public static SessionFactory sessionFactory;

    // set jdk logging level to DEBUG(FINE) pragmatically or via logging.properties
    static {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.FINE);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.FINE);
        }
    }

    public static void main(String[] args) {
        /* configure from hibernate.cfg.xml file

        SessionFactory sessionFactoryFromFile = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml")
                        .build()
        ).buildMetadata().buildSessionFactory(); */


        // configure from code
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect")
                .applySetting("hibernate.connection.url", "jdbc:postgresql://localhost:5432/hibernate_demo")
                .applySetting("hibernate.connection.username", "postgres")
                .applySetting("hibernate.connection.password", "postgres")
                .applySetting("hibernate.use_sql_comments", "true")
                .applySetting("hibernate.format_sql", "true")
                .applySetting("hibernate.hbm2ddl.auto", "create-drop")
                .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(Message.class);

        sessionFactory = metadataSources.buildMetadata().buildSessionFactory();


        // samples
        simpleTransactionExample();
        getAccessToMetadataExample();
    }

    private static void simpleTransactionExample() {
        // the same session in the same thread
        // session is closing automatically
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        // start transaction
        transaction.begin();

        Message message = new Message();

        session.persist(message);

        // deprecated
        List<Message> messagesDeprecated =
                session.createCriteria(
                        Message.class
                ).list();

        // HQL select example
        List<Message> messages =
                session.createQuery("from Message", Message.class).list();
        // auto-update
        messages.get(0).setText("Updated Hello World");

        // end transaction
        transaction.commit();

    }

    private static void getAccessToMetadataExample() {
        // dynamic
        System.out.println("DYNAMIC ACCESS");
        Session session = sessionFactory.openSession();
        Metamodel metamodel = session.getMetamodel();

        Set<ManagedType<?>> managedTypes = metamodel.getManagedTypes();

        for (ManagedType<?> managedType : managedTypes) {
            System.out.println(managedType.getPersistenceType());
            System.out.println(managedType.getJavaType());

            SingularAttribute<?, ?> id = managedType.getSingularAttribute("id");
            System.out.println(id.getJavaType());
            System.out.println(id.isId());
            System.out.println(id.getPersistentAttributeType().name());
        }

        // static
        System.out.println("STATIC ACCESS");
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();


        /* CriteriaExample non-static

        CriteriaQuery<Message> messageCriteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> fromMessage = messageCriteriaQuery.from(Message.class);
        messageCriteriaQuery.select(fromMessage);

        List<Message> messages = session.createQuery(messageCriteriaQuery).getResultList();
        messages.forEach(message -> System.out.println(message.getText()));

        // CriteriaExample WHERE
        Path<String> textPath = fromMessage.get("text");
        // where m.text like :text_pattern
        messageCriteriaQuery.where(
                criteriaBuilder.like(
                        textPath, criteriaBuilder.parameter(String.class, "text_pattern")
                )
        );

        messages = session.createQuery(messageCriteriaQuery)
                .setParameter("text_pattern", "%Hello World%")
                .getResultList();

        messages.forEach(message -> System.out.println(message.getText()));

        */

        // CriteriaExample static
        CriteriaQuery<Message> messageCriteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> fromMessage = messageCriteriaQuery.from(Message.class);
        messageCriteriaQuery.select(fromMessage);

        List<Message> messages = session.createQuery(messageCriteriaQuery).getResultList();
        messages.forEach(message -> System.out.println(message.getText()));

        // where m.text like :text_pattern
        messageCriteriaQuery.where(
                criteriaBuilder.like(
                        fromMessage.get(Message_.text), criteriaBuilder.parameter(String.class, "text_pattern")
                )
        );

        messages = session.createQuery(messageCriteriaQuery)
                .setParameter("text_pattern", "%Hello World%")
                .getResultList();

        messages.forEach(message -> System.out.println(message.getText()));
    }

}