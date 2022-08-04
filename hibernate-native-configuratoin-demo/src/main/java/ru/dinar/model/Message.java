package ru.dinar.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import ru.dinar.generator.CreationMessageGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
// These annotations force hibernate not to use prepared SQL statements at runtime
@DynamicUpdate
@DynamicInsert
// Marks class as Immutable class
// @Immutable
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @Column(insertable = false)
    @GeneratorType(type = CreationMessageGenerator.class, when = GenerationTime.INSERT)
    private String text;

    // ignore this field in JPA
    @Transient
    private Date date;

    // also ignore by JPA
    private transient Date updatedAt;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
