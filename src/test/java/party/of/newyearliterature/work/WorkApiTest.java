package party.of.newyearliterature.work;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    public void Givne_NotSignIn_When_Submit_Then_WorkAndUser() {
        // given
        String article = "article-123";
        String author = "author-123";
        String email = "email@gmail.com";
        String name = "email@gmail.com";
        String password = "password";

        UserDto userDto = UserDto.builder().email(email).name(name).password(password).build();
        WorkDto workDto = new WorkDto(article, author, userDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WorkDto> requestEntity = new HttpEntity<WorkDto>(workDto, headers);

        // when
        ResponseEntity<WorkDto> response = restTemplate
            .postForEntity("http://localhost:" + port + "/api/works", requestEntity, WorkDto.class);

        // then
        WorkDto resWorkDto = response.getBody();
        UserDto resUserDto = resWorkDto.getUserDto();

        assertEquals(article, resWorkDto.getArticle());
        assertEquals(author, resWorkDto.getAuthor());
        assertEquals(email, resUserDto.getEmail());
        assertEquals(name, resUserDto.getName());
    }

    @Test
    public void None_User_submit_BadRequest_test() {
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
        ResponseEntity<String> response = restTemplate.withBasicAuth("admin@of.com", "admin")
                .postForEntity("http://localhost:" + port + "/api/works", request, String.class);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void Given_SignIn_When_SubmitWork_Return_WorkAndUser(){
        // Given
        String article = "article-123";
        String author = "author-123";
        // 초기 데이터
        String email = "user@of.com";
        String password = "password";

        WorkCreateLoggedDto createDto = new WorkCreateLoggedDto();
        createDto.setArticle(article);
        createDto.setAuthor(author);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WorkCreateLoggedDto> requestEntity = new HttpEntity<>(createDto, headers);
        // When
        ResponseEntity<WorkDto> response = restTemplate
        .withBasicAuth(email, password)
        .postForEntity("/api/works/logged", requestEntity, WorkDto.class);
        // then
        WorkDto resWorkDto = response.getBody();
        UserDto resUserDto = resWorkDto.getUserDto();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(article, resWorkDto.getArticle());
        assertEquals(author, resWorkDto.getAuthor());
        assertEquals(email, resUserDto.getEmail());
    }

    @Test
    public void Given_CreatedAtDesc_When_GetWork_Then_WorkDtos() {
        // Given
        String sort = "createdAt,desc";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("sort", sort);

        // When
        ResponseEntity<String> response = restTemplate.withBasicAuth("user@of.com", "password")
                .getForEntity("http://localhost:" + port + "/api/works?sort={sort}", String.class, uriVariables);

        // Then
        ObjectMapper mapper = new ObjectMapper();
        WorkDto[] works = new WorkDto[0];
        try {
            works = mapper.readValue(response.getBody(), WorkDto[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(works.length > 0);
        for(int i=0; i<works.length-1; i++){
            assertTrue("정렬이 맞지 않습니다."
                , works[i].getCreatedAt() >= works[i+1].getCreatedAt());
        }
    }

}