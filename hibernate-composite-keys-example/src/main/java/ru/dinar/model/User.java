package ru.dinar.model;

import javax.persistence.*;

// example of composite primary key
@Entity
@Table(schema = "compositekey")
public class User {

    @EmbeddedId
    protected UserId userId;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "countryid",
            updatable = false,
            insertable = false,
            nullable = false
    )
    protected Country country;

    public User(UserId userId) {
        this.userId = userId;
    }

    public UserId getUserId() {
        return userId;
    }

    public Country getCountry() {
        return country;
    }

    protected User() {}

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", country=" + country +
                '}';
    }
}
