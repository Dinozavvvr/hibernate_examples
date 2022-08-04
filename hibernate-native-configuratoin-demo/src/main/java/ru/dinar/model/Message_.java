package ru.dinar.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 NOTE: metamodel class should be in the seme package as its entity class
 Alternatively you can enable org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor
 dependency with the according plugin
*/
@StaticMetamodel(Message.class)
public abstract class Message_ {

    public static volatile SingularAttribute<Message, Long> id;

    public static volatile SingularAttribute<Message, String> text;

}
