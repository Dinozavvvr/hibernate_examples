package ru.dinar.inheritance.tableperclasshierarchy;

import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator_type")
// another solution is @DiscriminatorFormula annotation
// @DiscriminatorFormula("case when cardNumber is not null then 'cc' else 'ba' end")
@Table(schema = "tableperclasshierarchy")
public abstract class BillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false)
    protected String owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
