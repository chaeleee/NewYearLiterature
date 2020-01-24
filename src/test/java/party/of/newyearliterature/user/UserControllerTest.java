package party.of.newyearliterature.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import party.of.newyearliterature.controller.UserController;
import party.of.newyearliterature.security.MyUserDetailsService;

/**
 * UserControllerTest
 */ 
@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private MyUserDetailsService myuserDetailsService;

    @Test
    public void Given_NotLogged_When_Logout_Then_BadRequestException() throws Exception {
        String logoutUrl = "/api/user/logout";
        mvc.perform(MockMvcRequestBuilders.get(logoutUrl))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void Given_NotLogged_When_GetUserInfo_Then_UnAuth() throws Exception {
        String userInfoUrl = "/api/user/me";
        mvc.perform(MockMvcRequestBuilders.get(userInfoUrl))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
}