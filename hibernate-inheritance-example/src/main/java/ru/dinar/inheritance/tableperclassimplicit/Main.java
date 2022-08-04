package ru.dinar.inheritance.tableperclassimplicit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import ru.dinar.inheritance.HibernateConfig;

import java.util.List;


public class Main {
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

        // there we can't do "select bd from BillingDetails db" coz of BillingDetails is not entity and hasn't own table and mapping in hibernate
        List<BillingDetails> billingDetails = session.createQuery("select cc from CreditCard cc", BillingDetails.class).list();
        for (BillingDetails billingDetail : billingDetails) {
            System.out.println(billingDetail.toString());
        }

        // end transaction
        session.getTransaction().commit();
    }
}