package party.of.newyearliterature.like;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.user.User;
import party.of.newyearliterature.work.Work;

/**
 * LikeRepoTest
 */
@DataJpaTest
@RunWith(SpringRunner.class)
public class LikeRepoTest {
    @Autowired TestEntityManager entityManager;
    @Autowired LikeRepository likeRepository;

    @Test
    public void Given_Like_When_Save_Then_EqualLike(){
        // given
        Like like = new Like();
        // when
        Like persist = likeRepository.save(like);
        // then
        assertEquals(like, persist);
    }

    @Test
    public void Given_Like_When_Save_Then_GetId(){
        // Given
        Like like = new Like();
        // when
        likeRepository.save(like);
        // then
        assertNotNull(like.getId());
    }

    @Test
    public void Given_LikeWithWorkAndUser_When_Save_Then_GetId(){
        // Given
        User user = new User("email", "name");
        entityManager.persist(user);
        Work work = new Work("article", "author", user);
        entityManager.persist(work);
        Like like = new Like(user, work);
        // When
        likeRepository.save(like);

        // Then
        assertNotNull(like.getId());
        assertEquals(user, like.getUser());
        assertEquals(work, like.getWork());
    }
}