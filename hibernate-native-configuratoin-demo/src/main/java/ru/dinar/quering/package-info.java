@org.hibernate.annotations.NamedQueries({ @org.hibernate.annotations.NamedQuery(
        name = "findMessagesOrderByName",
        query = "select i from Message i order by i.text asc" )
        })
package ru.dinar.quering;