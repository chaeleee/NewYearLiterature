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
        String nickname = "email@gmail.com";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        WorkDto workDto = new WorkDto();
        workDto.setArticle(article);
        workDto.setAuthor(author);
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setNickname(nickname);
        workDto.setUserDto(userDto);
        HttpEntity<WorkDto> requestEntity = new HttpEntity<WorkDto>(workDto, headers);

        // when
        ResponseEntity<WorkDto> response = restTemplate.postForEntity("http://localhost:"+port+"/api/work", requestEntity, WorkDto.class);

        // then
        WorkDto responseDto = response.getBody();
        assertEquals(article, responseDto.getArticle());
        assertEquals(author, responseDto.getAuthor());
        UserDto responseUserDto = responseDto.getUserDto();
        assertEquals(email, responseUserDto.getEmail());
        assertEquals(nickname, responseUserDto.getNickname());
    }

    
}