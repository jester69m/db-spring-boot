package com.springboot.generator;

import jakarta.persistence.PersistenceContext;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

public class MyIdGenerator implements IdentifierGenerator, Configurable {

    private Long nextId = 0L;
    private String prefix;
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj){
        List<Long> existingId = session.createQuery("SELECT e.id FROM Employee e",Long.class).getResultList();
        while(existingId.contains(nextId)){
            nextId+=2;
        }
        return (Serializable)(nextId);
    }
    @Override
    public void configure(Type type, Properties properties,
                          ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
    }

}
