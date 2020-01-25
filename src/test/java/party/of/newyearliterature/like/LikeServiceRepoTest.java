package party.of.newyearliterature.like;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserRepository;
import party.of.newyearliterature.work.Work;
import party.of.newyearliterature.work.WorkRepository;

/**
 * LikeServiceRepoTest
 */
@DataJpaTest
@RunWith(SpringRunner.class)
public class LikeServiceRepoTest {

    private LikeService likeService;
    
    @Autowired LikeRepository likeRepository;
    @Autowired TestEntityManager entityManager;
    
    @MockBean UserRepository userRepository;
    @MockBean WorkRepository workRepository;

    @Before
    public void setup(){
        likeService = new LikeServiceImpl(likeRepository, userRepository, workRepository);
    }

    @Test
    public void Given_LikeId_When_Delete_Then_Null(){
        // given
        User user = new User("email", "name");
        entityManager.persist(user);
        Work work = new Work("article", "author", user);
        entityManager.persist(work);
        Like like = new Like(user, work);
        entityManager.persist(like);

        // When
        likeService.delete(like.getId());

        // Then
        Like target = entityManager.find(Like.class, like.getId());
        assertNull(target);
    }

    @Test
    public void Save_Like_Test(){
        // Given
        Work work = new Work();
        work.setArticle("article");
        work.setAuthor("author");
        work = entityManager.persist(work);

        User user = new User();
        user.setEmail("email");
        user.setName("name");
        user = entityManager.persist(user);

        LikeCreateDto likeCreateDto = new LikeCreateDto();
        likeCreateDto.setUserEmail(user.getEmail());
        likeCreateDto.setWorkId(work.getId());

        when(workRepository.findById(likeCreateDto.getWorkId())).thenReturn(Optional.of(work));
        when(userRepository.findByEmail(likeCreateDto.getUserEmail())).thenReturn(Optional.of(user));

        // When
        LikeDto likeDto = likeService.save(likeCreateDto);

        // Then
        LikeId_Is_NotNull(likeDto);
        FindLike_Then_Equals_UserAndWork(work, user, likeDto);

    }

    public void LikeId_Is_NotNull(LikeDto likeDto){
        assertNotNull(likeDto.getId());
    }

    public void FindLike_Then_Equals_UserAndWork(Work work, User user, LikeDto likeDto){
        Like persist = entityManager.find(Like.class, likeDto.getId());
        assertEquals(user, persist.getUser());
        assertEquals(work, persist.getWork());
    }


}