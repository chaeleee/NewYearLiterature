package party.of.newyearliterature.like;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserRepository;
import party.of.newyearliterature.work.Work;
import party.of.newyearliterature.work.WorkRepository;

/**
 * LikeServiceTest
 */
@RunWith(SpringRunner.class)
public class LikeServiceTest {

    private LikeService likeService;

    @MockBean
    private LikeRepository likeRepository;
    @MockBean
    private WorkRepository workRepository;
    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp(){
        likeService = new LikeServiceImpl(likeRepository, userRepository, workRepository); 
    }

    @Test
    public void saveTest(){
        Given_LikeCreateDto_When_Save_Then_LikeDto(1L, "john", 1L);
        Given_LikeCreateDto_When_Save_Then_LikeDto(1L, "john", 2L);
        Given_LikeCreateDto_When_Save_Then_LikeDto(2L, "john", 2L);
        Given_LikeCreateDto_When_Save_Then_LikeDto(2L, "jane", 2L);
    }

    public void Given_LikeCreateDto_When_Save_Then_LikeDto(long workId, String username, long likeId){
        // given

        Work work = new Work();
        work.setId(workId);

        LikeCreateDto likeCreateDto = new LikeCreateDto();
        likeCreateDto.setWorkId(workId);
        likeCreateDto.setUsername(username);

        Like like = new Like();
        like.setId(likeId);

        when(likeRepository.save(any())).thenReturn(like);
        when(workRepository.findById(any())).thenReturn(Optional.of(work));
        // when
        LikeDto likeDto = likeService.save(likeCreateDto);
        // then
        assertEquals(like.getId(), likeDto.getId());
    }

    @Test
    public void deleteLikeTest(){
        Given_LikeId_When_Delete_Then_Like(1L, "username", 1L, 1L);
        Given_LikeId_When_Delete_Then_Like(2L, "username", 1L, 1L);
        
        Given_LikeId_When_Delete_Then_Like(1L, "john", 1L, 1L);
    }

    public void Given_LikeId_When_Delete_Then_Like(Long likeId, String username, Long userId, Long workId){
        // given
        User user = new User();
        user.setId(userId);
        user.setName(username);

        Like like = new Like();
        like.setUser(user);
        when(likeRepository.findById(likeId)).thenReturn(Optional.of(like));

        // when
        LikeDto likeDto = likeService.delete(likeId);

        // then
        assertEquals(likeId, likeDto.getId());
        assertEquals(username, likeDto.getUsername());
        assertEquals(userId, likeDto.getUserId());
        assertEquals(workId, likeDto.getWorkId());
    }

    

}