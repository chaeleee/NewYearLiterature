package party.of.newyearliterature.work;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Objects;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserDto;
import party.of.newyearliterature.user.UserRepository;

/**
 * TestWorkServiceRepo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkServiceRepoTest {

    @Autowired private WorkService workService;
    @Autowired private WorkRepository workRepository;
    @Autowired private UserRepository userRepository;

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

    @Test
    public void Given_UserAndWork_When_submitWork_Then_Is_WorkAndUser_In_DB(){
        // Given
        String email = "test@test.com";
        String name = "john";
        String password = "password123";
        String article = "떡볶이 먹고 싶다";
        String author = "퇴근길";
        UserDto userDto = UserDto.builder().email(email).name(name).password(password).build();
        WorkCreateDto workCreateDto = WorkCreateDto.builder().article(article).author(author).userDto(userDto).build();
        
        // When
        WorkDto resDto = workService.submit(workCreateDto);
        
        // Then
        Optional<User> userOpt = userRepository.findById(resDto.getUserDto().getId());
        assertTrue("유저가 DB에 존재해야 합니다.", userOpt.isPresent());
        User user = userOpt.get();
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(name, user.getName());

        Optional<Work> optWork = workRepository.findById(resDto.getId());
        assertTrue("Work가 DB에 존재해야 합니다.", optWork.isPresent());
        Work work = optWork.get();
        assertEquals(article, work.getArticle());
        assertEquals(author, work.getAuthor());
    }

    @Test
    public void Given_Work_When_submitWorkLogged_Then_Is_Work_In_DB(){
        // Given
        String email = "mukbang@test.com";
        String name = "식객";
        String password = "password123";

        String article = "떡볶이 먹고 싶다";
        String author = "퇴근길";

        User user = new User(email, name, password);
        user = userRepository.save(user);

        WorkCreateLoggedDto createDto = WorkCreateLoggedDto.builder().article(article).author(author).userEmail(email).build();

        // When
        WorkDto resDto = workService.submitLogged(createDto);
        
        // Then
        Optional<Work> optWork = workRepository.findById(resDto.getId());
        assertTrue("Work가 DB에 존재해야 합니다.", optWork.isPresent());
        Work work = optWork.get();
        assertEquals(article, work.getArticle());
        assertEquals(author, work.getAuthor());
    }
}