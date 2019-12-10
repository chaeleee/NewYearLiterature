package party.of.newyearliterature.like;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    public void Given_Like_When_Save_Then_LikeDto(){
        // given

        Work work = new Work();
        work.setId(1L);

        LikeCreateDto likeCreateDto = new LikeCreateDto();
        likeCreateDto.setWorkId(work.getId());
        likeCreateDto.setUsername("john");

        Like like = new Like();
        like.setId(2L);

        when(likeRepository.save(any())).thenReturn(like);
        when(workRepository.findById(any())).thenReturn(Optional.of(work));
        // when
        LikeDto saved = likeService.save(likeCreateDto);
        // then
        assertNotNull(saved);
        assertEquals(like.getId(), saved.getId());
    }


    @Test
    public void Given_LikeId_When_Delete_Then_Like(){
        
    }

}