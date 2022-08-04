package ru.dinar.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserId implements Serializable {

    protected String username;

    protected String phoneNumber;

    protected Long countryId;

    // used by users constructor
    public UserId(String username, String phoneNumber, Long countryId) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.countryId = countryId;
    }

    // non-argument constructor is required by hibernate specification for embeddable classes
    protected UserId() {}


    // we also have to override equals and hashcode methods coz of new equality rules


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return username.equals(userId.username) && phoneNumber.equals(userId.phoneNumber) && countryId.equals(userId.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, phoneNumber, countryId);
    }

    @Override
    public String toString() {
        return "UserId{" +
                "username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", countryId=" + countryId +
                '}';
    }
}

