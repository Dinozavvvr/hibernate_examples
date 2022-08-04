package ru.dinar.inheritance.tableperclasshierarchy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import ru.dinar.inheritance.HibernateConfig;

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

        metadataSources.addAnnotatedClass(BankAccount.class);
        metadataSources.addAnnotatedClass(CreditCard.class);
        metadataSources.addAnnotatedClass(BillingDetails.class);

        SessionFactory sessionFactory = metadataSources.buildMetadata().buildSessionFactory();



        Session session = sessionFactory.openSession();
        // begin transaction
        session.getTransaction().begin();

        // saving bank account
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankNumber("bank123");
        bankAccount.setAccount("BankUser");
        bankAccount.setOwner("Dinar");

        session.persist(bankAccount);

        // saving credit card
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber("visa123");
        creditCard.setExpMonth("oct");
        creditCard.setExpYear("2022");
        creditCard.setOwner("Dinar");

        session.persist(creditCard);

        List<BillingDetails> billingDetails = session.createQuery("select cc from BillingDetails cc", BillingDetails.class).list();
        for (BillingDetails billingDetail : billingDetails) {
            /* original select is:
            select
                billingdet0_.id as id2_0_,
                billingdet0_.owner as owner3_0_,
                billingdet0_.account as account4_0_,
                billingdet0_.bankNumber as banknumb5_0_,
                billingdet0_.cardNumber as cardnumb6_0_,
                billingdet0_.expMonth as expmonth7_0_,
                billingdet0_.expYear as expyear8_0_,
                billingdet0_.discriminator_type as discrimi1_0_
            from
                BillingDetails billingdet0_
             */
            System.out.println(billingDetail.toString());
        }

        // end transaction
        session.getTransaction().commit();
    }
}