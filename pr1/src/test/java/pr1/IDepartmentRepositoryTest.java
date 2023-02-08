package pr1;

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
import pr1.department.Department;
import pr1.department.IDepartmentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
public class DepartmentUniqueNameAndIdTest {

    private static Department department1;
    @Autowired
    private IDepartmentRepository iDepartmentRepository;
    @Autowired
    private ICustomRepository iCustomRepository;

    @BeforeEach
    void setUp() {
        department1 = new Department("name1",1,null);
        department1 = iDepartmentRepository.saveAndFlush(department1);
    }

    @Test
    void testNonUniqueName() {
        Department department2 = new Department("name1",2,null);
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> iDepartmentRepository.saveAndFlush(department2));
    }

    @Test
    void testNonUniqueCode() {
        Department department2 = new Department("name2",1   ,null);
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> iDepartmentRepository.saveAndFlush(department2));
    }

    @Test
    void testDeleteDepartment() {
        Custom custom1 = new Custom(50,10,10, customStatus.inProgress);
        Custom custom2 = new Custom(150,20,20, customStatus.inProgress);
        Custom custom3 = new Custom(250,30,30, customStatus.inProgress);
        List<Custom> customs = new ArrayList<>();
        customs.add(custom1);
        customs.add(custom2);
        customs.add(custom3);
        customs.forEach(department1::addCustom);
        iDepartmentRepository.saveAndFlush(department1);

        for (Custom custom : customs)
            Assertions.assertTrue(iCustomRepository.existsById(custom.getId()));

        iDepartmentRepository.delete(department1);

        for (Custom custom : customs)
            Assertions.assertFalse(iCustomRepository.existsById(custom.getId()));

    }
}