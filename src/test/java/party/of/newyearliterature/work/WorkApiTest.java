package party.of.newyearliterature.work;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.user.UserDto;

/**
 * WorkApiTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WorkApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 새유저_작품_제출_작품과유저정보_반환(){
        // given
        String article = "article-123";
        String author = "author-123";
        String email = "email@gmail.com";
        String name = "email@gmail.com";

        UserDto userDto = new UserDto(email, name);
        WorkDto workDto = new WorkDto(article, author, userDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<WorkDto> requestEntity = new HttpEntity<WorkDto>(workDto, headers);

        // when
        ResponseEntity<WorkDto> response = restTemplate.postForEntity("http://localhost:"+port+"/api/work", requestEntity, WorkDto.class);

        // then
        WorkDto resWorkDto = response.getBody();
        UserDto resUserDto = resWorkDto.getUserDto();
        
        assertEquals(article, resWorkDto.getArticle());
        assertEquals(author, resWorkDto.getAuthor());
        assertEquals(email, resUserDto.getEmail());
        assertEquals(name, resUserDto.getName());
    }

    @Test
    public void NoneUser_submit_test(){
        // given
        String article = "article-123";
        String author = "author-123";

        WorkDto workDto = new WorkDto();
        workDto.setArticle(article);
        workDto.setAuthor(author);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WorkDto> request = new HttpEntity<WorkDto>(workDto, headers);

        // when
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:"+port+"/api/work", request, String.class);
        
        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        

    }

    
}