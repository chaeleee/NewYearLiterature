package party.of.newyearliterature.work;

import static org.junit.Assert.assertFalse;

import java.util.Objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * WorkRepoTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class WorkRepoTest {

    @Autowired
    private WorkRepository workRepository;
    
    @Test public void Test_Work_Save(){
        // Given
        Work work = new Work("article", "author", null);
        // When
        work = workRepository.save(work);
        // Then
        assertFalse(Objects.isNull(work.getId()));
    }
}