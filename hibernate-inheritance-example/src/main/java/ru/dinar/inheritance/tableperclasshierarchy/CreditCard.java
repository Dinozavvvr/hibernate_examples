package ru.dinar.inheritance.tableperclasshierarchy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("credit_card")
@Table(schema = "tableperclasshierarchy")
public class CreditCard extends BillingDetails {

    // can not apply to this hierarchy strategy
    // @Column(nullable = false)
    protected String cardNumber;
    protected String expMonth;
    protected String expYear;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", expMonth='" + expMonth + '\'' +
                ", expYear='" + expYear + '\'' +
                ", id=" + id +
                ", owner='" + owner + '\'' +
                '}';
    }

}
