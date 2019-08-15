package party.of.newyearliterature.work;

import static org.junit.Assert.assertFalse;

import java.util.Objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.user.User;

/**
 * WorkRepoTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class WorkRepoTest {

    @Autowired
    private WorkRepository workRepository;

    
    @Test public void 작업과유저_등록_작업과유저정보반환(){
        // Given
        User user = new User("email", "name");
        Work work = new Work("article", "author", user);
        // When
        work = workRepository.save(work);
        // Then
        assertFalse(Objects.isNull(work.getId()));
        assertFalse(Objects.isNull(work.getUser().getId()));
    }
}