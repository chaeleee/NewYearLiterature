package party.of.newyearliterature.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.exception.BadRequestException;

/**
 * UserServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock 
    private UserRepository userRepository;
    
    private UserService userService;

    @Before
    public void setup(){
        userService = new UserServiceImpl(userRepository);
    }
    
    @Test
    public void BadRqeust_SignUp_Test(){
        UserDto passwordNull = UserDto.builder().email("email").password(null).name("name").build();
        UserDto emailNull = UserDto.builder().email(null).password("password").name("name").build();
        UserDto nameNull = UserDto.builder().email("email").password("password").name(null).build();
        UserDto allNull = UserDto.builder().email(null).password(null).name(null).build();
        UserDto allBlank = UserDto.builder().email("").password("").name("").build();

        NullOrBlank_SignUp_BadRequestException(passwordNull);
        NullOrBlank_SignUp_BadRequestException(emailNull);
        NullOrBlank_SignUp_BadRequestException(nameNull);
        NullOrBlank_SignUp_BadRequestException(allNull);
        NullOrBlank_SignUp_BadRequestException(allBlank);
    }

    private void NullOrBlank_SignUp_BadRequestException(UserDto userDto){
        try{
            userService.signUp(userDto);
            fail("should be returned BadRequestException");
        }catch(BadRequestException ex){
            assertTrue(ex instanceof BadRequestException); 
        }
    }

}