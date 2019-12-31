package party.of.newyearliterature.like;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

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


    @Test
    public void Delete_Like_Test(){
        // Given
        User user = new User("email", "name");
        entityManager.persist(user);
        Work work = new Work("article", "author", user);
        entityManager.persist(work);
        Like like = new Like(user, work);
        entityManager.persist(like);

        Given_Like_When_Delete_Then_FindLikeIsNull(like);

    }
    
    // TODO: FIXED IT
    public void Given_Like_When_Delete_Then_FindLikeIsNull(Like like){
        // when
        likeRepository.delete(like);
        Like persist = entityManager.find(Like.class, like.getId());
        // then
        assertNull(persist);
    }

    @Test
    public void Given_Likes_When_FindByWorkId_Then_Likes(){
        // Given
        User workAuthor = new User("email", "name", "password");
        entityManager.persist(workAuthor);
        Work work1 = new Work("article1", "author2", workAuthor);
        work1 = entityManager.persist(work1);

        User likeUser1 = new User("like@email.com", "likeUser1", "password");
        likeUser1 = entityManager.persist(likeUser1);
        Like like1 = new Like(likeUser1, work1);
        like1 = entityManager.persist(like1);

        // When
        List<Like> likes = likeRepository.findByWorkId(work1.getId());
        
        // Then
        Like like = likes.get(0);
        assertEquals(1, likes.size());
        assertTrue(like1.equals(like));
        assertTrue(work1.equals(like.getWork()));
        assertTrue(likeUser1.equals(like.getUser()));
    }
    
}