package party.of.newyearliterature.like;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserRepository;
import party.of.newyearliterature.work.Work;
import party.of.newyearliterature.work.WorkRepository;

/**
 * LIkeIntegrationTest
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class LIkeIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    WorkRepository workRepository;
    @Autowired
    LikeRepository likeRepsitory;
    @Autowired
    UserRepository userRepository;

    @Test
    public void saveTest() {
        // given
        Work work = new Work();
        work.setArticle("article");
        work.setAuthor("author");
        work = workRepository.save(work);

        LikeCreateDto likeCreateDto = new LikeCreateDto();
        likeCreateDto.setWorkId(work.getId());

        // when
        ResponseEntity<LikeDto> responseEntity = this.restTemplate.withBasicAuth("user@of.com", "password")
                .postForEntity("http://localhost:" + port + "/api/like", likeCreateDto, LikeDto.class);

        // then
        LikeDto responseBody = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseBody.getId());
    }

    @Test
    public void deleteTest() throws URISyntaxException {
        // given
        User user = new User();
        user.setName("john");
        user.setEmail("email");
        user = userRepository.save(user);
        Work work = new Work();
        work.setArticle("article");
        work.setAuthor("author");
        work = workRepository.save(work);
        Like like = new Like();
        like.setUser(user);
        like.setWork(work);
        like = likeRepsitory.save(like);
        // 
        RequestEntity<?> requestEntity = RequestEntity
            .delete(new URI("http://localhost:"+port+"/api/like/"+like.getId()))
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .build();
        ResponseEntity<String> responseEntity = this.restTemplate
            .withBasicAuth("user@of.com", "password")
            .exchange(requestEntity, String.class);
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}