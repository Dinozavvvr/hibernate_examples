package ru.dinar.inheritance.tableperclassimplicit;

import javax.persistence.*;

@Entity
@Table(schema = "tableperclassimplicit")
public class BankAccount extends BillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected String account;

    protected String bankNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", bankNumber='" + bankNumber + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
