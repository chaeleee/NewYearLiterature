package party.of.newyearliterature.work;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TestWorkServiceRepo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestWorkServiceRepo {

    @Autowired private WorkService workService;

    @Test public void 작업과유저등록_작업과유저ID반환(){
        // Given
        // UserDto userDto = new UserDto();
        // When
        // WorkDto resDto = workService.submit(workDto);
        // Then
    }
}