package pr1;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import pr1.custom.Custom;
import pr1.custom.ICustomRepository;
import pr1.custom.customStatus;
import pr1.customer.Customer;
import pr1.customer.ICustomerRepository;
import pr1.department.Department;
import pr1.department.IDepartmentRepository;
import pr1.recipient.IRecipientRepository;
import pr1.recipient.Recipient;
import pr1.truck.ITruckRepository;
import pr1.truck.Truck;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
@Transactional
public class IDepartmentRepositoryTest {

    private static Department department1;
    @Autowired
    private IDepartmentRepository iDepartmentRepository;
    @Autowired
    private ICustomRepository iCustomRepository;
    @Autowired
    private ICustomerRepository iCustomerRepository;
    @Autowired
    private IRecipientRepository iRecipientRepository;
    @Autowired
    private ITruckRepository iTruckRepository;

    @BeforeEach
    void setUp() {
        department1 = new Department("name1",1,new ArrayList<>());
        department1 = iDepartmentRepository.saveAndFlush(department1);
    }

    @Test
    void testNonUniqueName() {
        Department department2 = new Department("name1",2,new ArrayList<>());
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> iDepartmentRepository.saveAndFlush(department2));
    }

    @Test
    void testNonUniqueCode() {
        Department department2 = new Department("name2",1   ,new ArrayList<>());
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> iDepartmentRepository.saveAndFlush(department2));
    }

    @Test
    void testDeleteDepartment() {
        Customer c1 = new Customer();
        iCustomerRepository.saveAndFlush(c1);
        Recipient r1 = new Recipient();
        iRecipientRepository.saveAndFlush(r1);
        Truck t1 = new Truck();
        iTruckRepository.saveAndFlush(t1);

        Custom custom1 = new Custom(50,10,10, customStatus.inProgress,c1,r1,t1,department1);
//        Custom custom1 = new Custom(50,10,10, customStatus.inProgress);
        iCustomRepository.saveAndFlush(custom1);
        Custom custom2 = new Custom(150,20,20, customStatus.recent,c1,r1,t1,department1);
//        Custom custom2 = new Custom(150,20,20, customStatus.recent);
        iCustomRepository.saveAndFlush(custom2);
        List<Custom> customs = new ArrayList<>();
        customs.add(custom1);
        customs.add(custom2);
        for(Custom custom : customs)
            department1.addCustom(custom);
        iDepartmentRepository.saveAndFlush(department1);

        for (Custom custom : customs)
            Assertions.assertTrue(iCustomRepository.existsById(custom.getId()));

        iDepartmentRepository.delete(department1);

        for (Custom custom : customs)
            Assertions.assertFalse(iCustomRepository.existsById(custom.getId()));

    }
}