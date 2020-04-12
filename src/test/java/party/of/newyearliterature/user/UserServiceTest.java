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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.exception.BadRequestException;
import party.of.newyearliterature.role.Role;
import party.of.newyearliterature.role.RoleBasicType;
import party.of.newyearliterature.role.RoleRepository;

/**
 * UserServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired 
    private UserRepository userRepository;
    
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setup(){
        userService = new UserServiceImpl(userRepository, passwordEncoder, roleRepository);
    }
    
    @Test
    public void Given_NullOrBlank_When_SignUp_Then_Throw_BadRqeustException(){
        Role userType = new Role(RoleBasicType.USER.getName());

        UserDto passwordNull = UserDto.builder().email("email").password(null).name("name").role(userType).build();
        UserDto emailNull = UserDto.builder().email(null).password("password").name("name").role(userType).build();
        UserDto nameNull = UserDto.builder().email("email").password("password").name(null).role(userType).build();
        UserDto allNull = UserDto.builder().email(null).password(null).name(null).role(userType).build();
        UserDto allBlank = UserDto.builder().email("").password("").name("").role(userType).build();

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

    @Test
    public void 유저이메일이_이미_존재할_때_가입_시_BadRequestException(){
        // given
        String email = "lee@gmail.com";
        String password = "123";
        String name = "leeyun";
        Role userRole = roleRepository.findByName(RoleBasicType.USER.getName());
        User persistUser = new User(email, password, name, userRole);
        userRepository.save(persistUser);

        UserDto userDto = UserDto.builder().email(email).password(password).name(name).role(userRole).build();
        
        try{
            userService.signUp(userDto);
            fail("should be returned BadRequestException");
        }catch(BadRequestException ex){
            assertTrue(ex instanceof BadRequestException);
        }
    }

}