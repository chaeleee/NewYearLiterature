package party.of.newyearliterature.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.role.Role;
import party.of.newyearliterature.user.User;

/**
 * TestRestSecurity
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestRestSecurity {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MyUserDetailsService myUserDetailsService;

    private String userInfoUrl = "/api/user/me";
    private String logoutUrl = "/api/user/logout";


    @Test
    public void Test_Login(){
        Role userRole = new Role("user");
        User user1 = new User("user@of.com", passwordEncoder.encode("password"), "user1", userRole);
        
        Role adminRole = new Role("admin");
        User admin1 = new User("admin@of.com", passwordEncoder.encode("admin"), "admin1", adminRole);
        when(myUserDetailsService.loadUserByUsername(user1.getEmail())).thenReturn(new MyUserPrincipal(user1));
        when(myUserDetailsService.loadUserByUsername(admin1.getEmail())).thenReturn(new MyUserPrincipal(admin1));

        Given_InValidUser_When_login_Then_UnAuth("annonynous","___");
        Given_InValidUser_When_login_Then_UnAuth("","");
        Given_ValidUser_When_login_Then_OK("admin@of.com", "admin");
        Given_ValidUser_When_login_Then_OK("user@of.com", "password");
    }

    private void Given_ValidUser_When_login_Then_OK(String userEmail, String password){
        ResponseEntity<String> response = this.restTemplate
                                            .withBasicAuth(userEmail, password)
                                            .getForEntity(userInfoUrl, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void Given_InValidUser_When_login_Then_UnAuth(String userEmail, String password){
        ResponseEntity<String> response = this.restTemplate
                                            .withBasicAuth(userEmail, password)
                                            .getForEntity(userInfoUrl, String.class); 
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void Given_Login_When_Logout_Then_Principal_is_null(){
        // Given: Login
        Role userRole = new Role("user");
        User user1 = new User("user@of.com", passwordEncoder.encode("password"), "user1", userRole);
        when(myUserDetailsService.loadUserByUsername(user1.getEmail())).thenReturn(new MyUserPrincipal(user1));
        this.restTemplate
            .withBasicAuth(user1.getEmail(), user1.getPassword())
            .getForEntity(userInfoUrl, String.class);

        // When: Logout
        ResponseEntity<String> response = this.restTemplate
            .getForEntity(logoutUrl, String.class);
        
        // Then: Principal is null
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

}