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
import party.of.newyearliterature.like.Like;
import party.of.newyearliterature.like.LikeRepository;
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

    @MockBean 
    private WorkRepository workRepo;
    
    @MockBean 
    private LikeRepository likeRepo;
    
    @Autowired 
    private UserService userService;

    @Before public void setup(){
        service = new WorkServiceImpl(workRepo, userService, likeRepo);
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
        Work work = new Work(1L, "article1", "author2", null, null);
        List<Work> persists = new ArrayList<>();
        persists.add(work);
        when(workRepo.findByAuthorContaining(any(), any())).thenReturn(persists);

        User likedUser = new User("user@of.com", "user");
        Like likeForWork1 = new Like(likedUser, work);
        List<Like> likesForWork1 = new ArrayList<>();
        likesForWork1.add(likeForWork1);
        when(likeRepo.findByWorkId(work.getId())).thenReturn(likesForWork1);

        // when
        List<WorkDto> workDtos = service.getAll(null, null, null);

        // then
        WorkDto workDto = workDtos.get(0);
        assertEquals(work.getId(), workDto.getId());
        assertEquals(work.getArticle(), workDto.getArticle());
        assertEquals(likesForWork1.size(), workDto.getNumOfLikes().intValue());
    }

    @Test
    public void getWorksByLoginUser(){
        // given
        Work work = new Work(1L, "article1", "author2", null, null);
        List<Work> persists = new ArrayList<>();
        persists.add(work);
        when(workRepo.findByAuthorContaining(any(), any())).thenReturn(persists);

        User likedUser = new User("user@of.com", "user");
        Like likeForWork1 = new Like(likedUser, work);
        List<Like> likesForWork1 = new ArrayList<>();
        likesForWork1.add(likeForWork1);
        when(likeRepo.findByWorkId(work.getId())).thenReturn(likesForWork1);

        // When & Then
        When_getAll_Then_isLiked(likedUser.getEmail(), true);
        When_getAll_Then_isLiked(null, false);

    }

    private void When_getAll_Then_isLiked(String loginUserEmail, boolean expectedIsLiked){
        // when
        List<WorkDto> workDtos = service.getAll(null, null, loginUserEmail);

        // then
        WorkDto workDto = workDtos.get(0);
        assertEquals(expectedIsLiked, workDto.getIsLiked());
    }

}