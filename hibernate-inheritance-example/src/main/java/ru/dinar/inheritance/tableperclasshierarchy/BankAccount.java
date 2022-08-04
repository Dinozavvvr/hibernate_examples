package ru.dinar.inheritance.tableperclasshierarchy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("bank_account")
@Table(schema = "tableperclasshierarchy")
public class BankAccount extends BillingDetails {

    protected String account;

    protected String bankNumber;

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
                "account='" + account + '\'' +
                ", bankNumber='" + bankNumber + '\'' +
                ", id=" + id +
                ", owner='" + owner + '\'' +
                '}';
    }

}

