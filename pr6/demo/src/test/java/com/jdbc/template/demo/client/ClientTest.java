package com.jdbc.template.demo.client;

import com.jdbc.template.demo.client.Client;
import com.jdbc.template.demo.client.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ClientTest {

    @Autowired
    ClientRepository repository;

    @BeforeEach
    void init(){
        Client first = new Client();
        first.setFirstName("f_name1");
        first.setSecondName("s_name1");
        first.setAge(16);
        first.setBalance(0);
        first.setVipStatus(false);
        repository.saveAndFlush(first);

        Client second = new Client();
        second.setFirstName("f_name2");
        second.setSecondName("s_name2");
        second.setAge(26);
        second.setBalance(10000000);
        second.setVipStatus(true);
        repository.saveAndFlush(second);

        Client third = new Client();
        third.setFirstName("f_name1");
        third.setSecondName("s_name3");
        third.setAge(17);
        third.setBalance(20000);
        third.setVipStatus(false);
        repository.saveAndFlush(third);

        Client fourth = new Client();
        fourth.setFirstName("f_name4");
        fourth.setSecondName("s_name4");
        fourth.setAge(40);
        fourth.setBalance(100000);
        fourth.setVipStatus(false);
        repository.saveAndFlush(fourth);
    }

    @AfterEach
    void destruct(){
        repository.deleteAll();
    }

    @Test
    void findAllByFirstNameTest(){
        List<Client> fromDB = repository.findAllByFirstName("f_name1");
        Assertions.assertEquals(2, fromDB.size());
    }

    @Test
    void findClientByAgeGreaterThanEqualTest(){
        int age = 18;
        List<Client> fromDB = repository.findClientByAgeGreaterThanEqual(age);
        for(Client cl : fromDB){
            Assertions.assertTrue(cl.getAge() >= age);
        }
    }

    @Test
    void countAllWithVipStatus(){
        int count = repository.countAllByVipStatusTrue();
        Assertions.assertEquals(1, count);
    }

}
