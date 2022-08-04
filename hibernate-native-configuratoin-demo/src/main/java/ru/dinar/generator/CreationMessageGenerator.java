package ru.dinar.generator;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

public class CreationMessageGenerator implements ValueGenerator<String> {

    @Override
    public String generateValue(Session session, Object owner) {
        return "Hello world message";
    }

}
