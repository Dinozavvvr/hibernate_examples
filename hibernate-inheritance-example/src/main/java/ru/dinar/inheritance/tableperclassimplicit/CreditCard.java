package ru.dinar.inheritance.tableperclassimplicit;

import javax.persistence.*;

@Entity
// overrides settings of owner property in super class
@AttributeOverride(
        name = "owner",
        column = @Column(name = "cc_owner", nullable = false)
)
@Table(schema = "tableperclassimplicit")
public class CreditCard extends BillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", expMonth='" + expMonth + '\'' +
                ", expYear='" + expYear + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
