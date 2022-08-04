package ru.dinar.inheritance.tableperclassunions;

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

        // there we can do "select bd from BillingDetails db" coz of BillingDetails now is entity
        List<BillingDetails> billingDetails = session.createQuery("select cc from BillingDetails cc", BillingDetails.class).list();
        for (BillingDetails billingDetail : billingDetails) {
            /* original select is:

            select billingdet0_.id         as id1_2_,
                   billingdet0_.owner      as owner2_2_,
                   billingdet0_.account    as account1_0_,
                   billingdet0_.bankNumber as banknumb2_0_,
                   billingdet0_.cardNumber as cardnumb1_1_,
                   billingdet0_.expMonth   as expmonth2_1_,
                   billingdet0_.expYear    as expyear3_1_,
                   billingdet0_.clazz_     as clazz_
            from (select id,
                         owner,
                         account,
                         bankNumber,
                         null::varchar as cardNumber,
                         null::varchar as expMonth,
                         null::varchar as expYear,
                         1             as clazz_
                  from tableperclassunions.BankAccount
                  union
                      all
                  select id,
                         owner,
                         null::varchar as account,
                         null::varchar as bankNumber,
                         cardNumber,
                         expMonth,
                         expYear,
                         2             as clazz_
                  from tableperclassunions.CreditCard) billingdet0_
            */

            // result is
            // BankAccount{account='BankUser', bankNumber='bank123', id=1, owner='Dinar'}
            // CreditCard{cardNumber='visa123', expMonth='oct', expYear='2022', id=2, owner='Dinar'}
            System.out.println(billingDetail.toString());
        }

        // end transaction
        session.getTransaction().commit();
    }
}