package party.of.newyearliterature.work;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserDto;
import party.of.newyearliterature.user.UserRepository;

/**
 * WorkServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkServiceTest {

    private WorkService service;

    @MockBean private WorkRepository workRepo;

    @MockBean private UserRepository userRepo;

    @Before public void setup(){
        service = new WorkServiceImpl(workRepo, userRepo);
    }

    @Test public void 유저와작업물등록_작업물과유저정보반환(){
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("email");

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());

        Work work = new Work();
        work.setId(1L);
        work.setArticle("article");
        work.setAuthor("author");
        work.setCreatedAt(LocalDateTime.ofEpochSecond(System.currentTimeMillis(), 0, ZoneOffset.UTC));
        work.setUser(user);

        WorkDto dto = new WorkDto();
        dto.setId(work.getId());
        dto.setArticle(work.getArticle());
        dto.setAuthor(work.getAuthor());
        dto.setUserDto(userDto);     

        when(workRepo.save(any(Work.class))).thenReturn(work);

        // When
        WorkDto res = service.submit(dto);

        // Then
        assertEquals(dto.getArticle(), res.getArticle());
        assertEquals(dto.getUserDto().getEmail(), res.getUserDto().getEmail());
    }

    @Test public void 작업물본문누락_유저와작업물등록_BadReuqest에러발생(){
        // Given
        
        // When

        // Then
    }

}