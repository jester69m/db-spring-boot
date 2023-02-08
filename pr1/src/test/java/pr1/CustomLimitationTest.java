package pr1;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pr1.custom.Custom;
import pr1.custom.ICustomRepository;
import pr1.custom.customStatus;

public class MinPriceTest {

    @Autowired
    ICustomRepository iCustomRepository;

    @Test
    public void minCustomPriceTest() throws Exception{
        Custom custom = new Custom(55,10,10, customStatus.inProgress);
        iCustomRepository.save(custom);


    }
}
