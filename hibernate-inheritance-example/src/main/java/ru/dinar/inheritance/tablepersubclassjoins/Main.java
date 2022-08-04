package ru.dinar.inheritance.tablepersubclassjoins;

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

            select billingdet0_.id           as id1_1_,
                   billingdet0_.owner        as owner2_1_,
                   billingdet0_1_.account    as account1_0_,
                   billingdet0_1_.bankNumber as banknumb2_0_,
                   billingdet0_2_.cardNumber as cardnumb1_2_,
                   billingdet0_2_.expMonth   as expmonth2_2_,
                   billingdet0_2_.expYear    as expyear3_2_,
                   case
                       when billingdet0_1_.id is not null then 1
                       when billingdet0_2_.cc_id is not null then 2
                       when billingdet0_.id is not null then 0
                       end                   as clazz_
            from tablepersubclassjoins.BillingDetails billingdet0_
                     left outer join
                 tablepersubclassjoins.BankAccount billingdet0_1_ on billingdet0_.id = billingdet0_1_.id
                     left outer join
                 tablepersubclassjoins.CreditCard billingdet0_2_ on billingdet0_.id = billingdet0_2_.cc_id
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