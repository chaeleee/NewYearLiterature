package party.of.newyearliterature.work;

import static org.junit.Assert.assertFalse;

import java.util.Objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.user.UserDto;

/**
 * TestWorkServiceRepo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkServiceRepoTest {

    @Autowired private WorkService workService;

    @Test public void 작업과유저등록_작업과유저ID반환(){
        // Given
        UserDto userDto = UserDto.builder().email("email").name("name").password("password").build();
        WorkCreateDto workCreateDto = new WorkCreateDto();
        workCreateDto.setArticle("article");
        workCreateDto.setAuthor("author");
        workCreateDto.setUserDto(userDto);
        // When
        WorkDto resDto = workService.submit(workCreateDto);
        // Then
        assertFalse(Objects.isNull(resDto.getId()));
        assertFalse(Objects.isNull(resDto.getUserDto().getId()));
    }
}