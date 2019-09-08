package party.of.newyearliterature.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TestRestSecurity
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestRestSecurity {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void test_UnAuth(){
        ResponseEntity<String> response = this.restTemplate.getForEntity("/api/user/secure", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void test_mock_auth(){
        ResponseEntity<String> response = this.restTemplate.withBasicAuth("admin@of.com", "admin")
                                            .getForEntity("/api/user/secure", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void test_mock_UnAuth(){
        ResponseEntity<String> response = this.restTemplate.withBasicAuth("annonymous", "123")
                                            .getForEntity("/api/user/secure", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}