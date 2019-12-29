package party.of.newyearliterature.work;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.exception.BadRequestException;
import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserDto;
import party.of.newyearliterature.user.UserService;

/**
 * WorkServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkServiceTest {

    private WorkService service;

    @MockBean private WorkRepository workRepo;
    @Autowired private UserService userService;

    @Before public void setup(){
        service = new WorkServiceImpl(workRepo, userService);
    }

    @Test public void WorkAndUser_Submit_Return_WorkAndUser(){
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("email");
        user.setName("name");
        user.setPassword("password");

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        Work work = new Work();
        work.setId(1L);
        work.setArticle("article");
        work.setAuthor("author");
        work.setCreatedAt(LocalDateTime.ofEpochSecond(System.currentTimeMillis(), 0, ZoneOffset.UTC));
        work.setUser(user);

        WorkCreateDto dto = new WorkCreateDto();
        // dto.setId(work.getId());
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
    
    @Test(expected = BadRequestException.class)
    public void ArticleIsNull_UserAndWork_Submit_Return_BadReuqestException(){
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("email");

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());

        Work work = new Work();
        work.setId(1L);
        work.setArticle("");
        work.setAuthor("author");
        work.setCreatedAt(LocalDateTime.ofEpochSecond(System.currentTimeMillis(), 0, ZoneOffset.UTC));
        work.setUser(user);

        WorkCreateDto dto = new WorkCreateDto();
        // dto.setId(work.getId());
        dto.setArticle(work.getArticle());
        dto.setAuthor(work.getAuthor());
        dto.setUserDto(userDto);     

        when(workRepo.save(any(Work.class))).thenReturn(work);

        // When
        service.submit(dto);

        // Then
        // throw BadRequestException
    }

    // 유저정보 미입력, 작품제출, 잘못된 요청 반환
    @Test(expected = BadRequestException.class)
    public void UserIsNull_Submit_Return_BadReuqestException(){
        WorkCreateDto dto = new WorkCreateDto();
        dto.setArticle("article");
        dto.setAuthor("author");
        dto.setUserDto(null);
        
        //when
        service.submit(dto);
    }

    @Test(expected = BadRequestException.class)
    public void PasswordIsNull_WorkAndUser_Submit_Return_BadRequestException(){
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("email");
        user.setPassword(null);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());

        Work work = new Work();
        work.setId(1L);
        work.setArticle("article");
        work.setAuthor("author");
        work.setCreatedAt(LocalDateTime.ofEpochSecond(System.currentTimeMillis(), 0, ZoneOffset.UTC));
        work.setUser(user);

        WorkCreateDto dto = new WorkCreateDto();
        dto.setArticle(work.getArticle());
        dto.setAuthor(work.getAuthor());
        dto.setUserDto(userDto);     

        when(workRepo.save(any(Work.class))).thenReturn(work);

        // When
       service.submit(dto);

    }

    @Test
    public void getWorksTest(){
        // given
        List<Work> persists = new ArrayList<>();
        // Sort sort = new Sort(Direction.DESC, "createdAt");
        LocalDateTime now = LocalDateTime.now();
        persists.add(new Work(3L, "article3", "author3", now.plusHours(2L), null));
        persists.add(new Work(2L, "article2", "author2", now.plusHours(1L), null));
        persists.add(new Work(1L, "article1", "author2", now, null));
        
        when(workRepo.findByAuthorContaining(any(), any())).thenReturn(persists);

        // when
        List<WorkDto> works = service.getAll(null, null);

        // then
        for(int i=0; i<works.size(); i++){
            assertEquals(persists.get(i).getId(), works.get(i).getId());
            assertEquals(persists.get(i).getArticle(), works.get(i).getArticle());
        }
    }


}