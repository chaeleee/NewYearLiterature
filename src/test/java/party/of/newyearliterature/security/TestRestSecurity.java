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

    private String getUserInfoUrl = "/api/user/me";

    @Test
    public void Test_Login(){
        Given_inValidUser_When_login_Then_UnAuth("annonynous","___");
        Given_inValidUser_When_login_Then_UnAuth("","");
        Given_validUser_When_login_Then_OK("admin@of.com", "admin");
        Given_validUser_When_login_Then_OK("user@of.com", "password");
    }

    private void Given_validUser_When_login_Then_OK(String userEmail, String password){
        ResponseEntity<String> response = this.restTemplate.withBasicAuth(userEmail, password)
                                            .getForEntity(getUserInfoUrl, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void Given_inValidUser_When_login_Then_UnAuth(String userEmail, String password){
        ResponseEntity<String> response = this.restTemplate.withBasicAuth(userEmail, password)
                                            .getForEntity(getUserInfoUrl, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

}