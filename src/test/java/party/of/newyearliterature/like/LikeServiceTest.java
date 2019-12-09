package party.of.newyearliterature.like;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.user.User;
import party.of.newyearliterature.work.Work;

/**
 * LikeServiceTest
 */
@RunWith(SpringRunner.class)
public class LikeServiceTest {

    // @TestConfiguration
    // static class LikeServiceTestContextConfiguration{
    //     @Bean
    //     public LikeService likeService(){
    //         return new LikeServiceImpl();
    //     }
    // }

    private LikeService likeService;

    @MockBean
    private LikeRepository likeRepository;

    @Before
    public void setUp(){
        likeService = new LikeServiceImpl(likeRepository); 
        // Like like = new Like();
        // Mockito.when(likeRepository.save(Mockito.any())).thenReturn(Mockito.any());
    }

    @Test
    public void Given_Like_When_Save_Then_Like(){
        // given
        User user = new User("email", "name", "password");
        Work work = new Work("article", "author", user);
        Like like = new Like(user, work);
        // when
        Like saved = likeService.save(like);
        // then
        assertEquals(user.getEmail(), saved.getUser().getEmail());
        assertEquals(user.getName(), saved.getUser().getName());
        assertEquals(work.getArticle(), saved.getWork().getArticle());        
        assertEquals(work.getAuthor(), saved.getWork().getAuthor());
    }

}