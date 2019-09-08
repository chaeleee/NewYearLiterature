package party.of.newyearliterature.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserDto;
import party.of.newyearliterature.user.UserService;

/**
 * UserController
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/user/secure")
    public User login(){
        User user = new User();
        user.setEmail("email");
        return user;
    }

    @GetMapping("/api/user/me")
    public UserDto getLoggedUser(Principal principal){
        String email = principal.getName();
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        return userService.getByEmail(userDto);
    }
}