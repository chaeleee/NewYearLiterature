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
public class TestWorkServiceRepo {

    @Autowired private WorkService workService;

    @Test public void 작업과유저등록_작업과유저ID반환(){
        // Given
        UserDto userDto = new UserDto();
        userDto.setEmail("email");
        userDto.setNickname("nickname");
        WorkDto workDto = new WorkDto();
        workDto.setArticle("article");
        workDto.setAuthor("author");
        workDto.setUserDto(userDto);
        // When
        WorkDto resDto = workService.submit(workDto);
        // Then
        assertFalse(Objects.isNull(resDto.getId()));
        assertFalse(Objects.isNull(resDto.getUserDto().getId()));
    }
}