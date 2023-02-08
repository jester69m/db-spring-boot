package pr1;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class CustomLimitationTest {

    private static Custom custom1;
    private static Customer c1;
    private static Recipient r1;
    private static Truck t1;
    private static Department d1;

    @Autowired
    private ICustomRepository iCustomRepository;
    @Autowired
    private ICustomerRepository iCustomerRepository;
    @Autowired
    private IRecipientRepository iRecipientRepository;
    @Autowired
    private ITruckRepository iTruckRepository;
    @Autowired
    private IDepartmentRepository iDepartmentRepository;

    @BeforeEach
    void setUp() {
        t1 = new Truck(10,10);
        iTruckRepository.saveAndFlush(t1);
        c1 = new Customer();
        iCustomerRepository.saveAndFlush(c1);
        r1 = new Recipient();
        iRecipientRepository.saveAndFlush(r1);
        d1 = new Department("d1",1,new ArrayList<>());
        iDepartmentRepository.saveAndFlush(d1);

        custom1 = new Custom(50,1,1, customStatus.recent,c1,r1,t1,d1);
//        custom1 = new Custom(50,1,1, customStatus.recent);
    }

    @Test
    void testValidPriceCustom() {
        Assertions.assertDoesNotThrow(() -> iCustomRepository.saveAndFlush(custom1));
    }

    @Test
    void testInvalidPriceCustom() {
        custom1.setPrice(49);

        Assertions.assertThrows(ConstraintViolationException.class,
                () -> iCustomRepository.saveAndFlush(custom1));
    }

    @Test
    void testInvalidWeight() {
        custom1.setWeight(-1);

        Assertions.assertThrows(ConstraintViolationException.class,
                () -> iCustomRepository.saveAndFlush(custom1));
    }

    @Test
    void testInvalidVolume() {
        custom1.setVolume(-1);

        Assertions.assertThrows(ConstraintViolationException.class,
                () -> iCustomRepository.saveAndFlush(custom1));
    }
}