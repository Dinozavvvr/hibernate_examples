package ru.dinar.model;

import javax.persistence.*;

// example of composite foreign key
@Entity
@Table(schema = "compositekey")
public class Item {

    @Id
    @GeneratedValue
    protected Long id;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "user_username", referencedColumnName = "username"),
            @JoinColumn(name = "user_phone", referencedColumnName = "phonenumber"),
            @JoinColumn(name = "user_country_id", referencedColumnName = "countryid")
    })
    protected User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", owner=" + owner +
                '}';
    }
}
