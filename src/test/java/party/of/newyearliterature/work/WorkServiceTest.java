package party.of.newyearliterature.work;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.exception.BadRequestException;
import party.of.newyearliterature.like.Like;
import party.of.newyearliterature.like.LikeRepository;
import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserDto;
import party.of.newyearliterature.user.UserRepository;
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
    
    @MockBean 
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before public void setup(){
        service = new WorkServiceImpl(workRepo, userService, likeRepo, userRepository);
    }

    @Test public void Given_WorkAndUser_When_Submit_Then_Return_WorkAndUser(){
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
    public void Given_ArticleIsNull_When_Submit_UserAndWork_Then_Return_BadReuqestException(){
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
    public void Given_UserIsNull_When_Submit_Then_Throw_BadReuqestException(){
        WorkCreateDto dto = new WorkCreateDto();
        dto.setArticle("article");
        dto.setAuthor("author");
        dto.setUserDto(null);
        
        //when
        service.submit(dto);
    }

    @Test
    public void Given_Null_When_Get_Works_Then_Return_WorkDtos(){
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
    public void Given_WorkId_LoggedIn_When_Get_Works_Then_Return_isLiked(){
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

    @Test
    public void Given_LikesSort_When_GetAll_Then_WorkDtos_SortedByLikes(){
        // Given
        Work work1 = new Work(1L, "article", "author", null);
        Work work2 = new Work(2L, "article", "author", null);
        Work work3 = new Work(3L, "article", "author", null);
        List<Work> works = new ArrayList<>();
        works.add(work1);
        works.add(work2);
        works.add(work3);

        User mockUser = new User("email", "name");
        Like like1 = new Like(mockUser, null);
        Like like2 = new Like(mockUser, null);
        Like like3 = new Like(mockUser, null);
        List<Like> threeLikes = new ArrayList<>();
        threeLikes.add(like1);
        threeLikes.add(like2);
        threeLikes.add(like3);
        List<Like> twoLikes = new ArrayList<>();
        twoLikes.add(like1);
        twoLikes.add(like2);

        
        Sort sort = new Sort(Direction.DESC, "likes");
        when(workRepo.findByAuthorContaining(any(), any())).thenReturn(works);
        when(likeRepo.findByWorkId(3L)).thenReturn(threeLikes);
        when(likeRepo.findByWorkId(2L)).thenReturn(twoLikes);
        
        // When
        List<WorkDto> workDtos = service.getAll(null, sort, null);

        // Then
        assertEquals(work3.getId(), workDtos.get(0).getId());
        assertEquals(work2.getId(), workDtos.get(1).getId());

    }

    @Test
    public void Given_WorkCreateDto_When_SubmitLogged_Then_WorkDto(){
        // Given
        WorkCreateLoggedDto createDto = new WorkCreateLoggedDto();
        createDto.setArticle("article");
        createDto.setAuthor("author");
        createDto.setUserEmail("user@of.com");

        User user = new User();
        user.setEmail(createDto.getUserEmail());
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        Work work = new Work();
        work.setArticle(createDto.getArticle());
        work.setAuthor(createDto.getAuthor());
        work.setUser(user);
        when(workRepo.save(any(Work.class))).thenReturn(work);

        // When
        WorkDto workDto = service.submitLogged(createDto);
        // Then
        assertEquals(createDto.getArticle(), workDto.getArticle());
        assertEquals(createDto.getAuthor(), workDto.getAuthor());
        assertEquals(createDto.getUserEmail(), workDto.getUserDto().getEmail());
    }

}