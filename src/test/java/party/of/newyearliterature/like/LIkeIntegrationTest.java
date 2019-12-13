package party.of.newyearliterature.like;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

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
    WorkRepository workreRepository;
    
    @Test
    public void saveTest(){
        // given
        Work work = new Work();
        work.setArticle("article");
        work.setAuthor("author");
        work = workreRepository.save(work);

        LikeCreateDto likeCreateDto = new LikeCreateDto();
        likeCreateDto.setWorkId(work.getId());
        
        // when
        ResponseEntity<LikeDto> responseEntity = this.restTemplate.postForEntity("http://localhost:"+port+"/api/like", likeCreateDto, LikeDto.class);
        
        // then
        LikeDto responseBody = responseEntity.getBody();
        assertEquals(200, responseEntity.getStatusCode());
        assertNotNull(responseBody.getId());
    }
}