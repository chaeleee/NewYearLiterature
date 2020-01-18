package party.of.newyearliterature.controller;

import java.security.Principal;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.user.UserDto;
import party.of.newyearliterature.user.UserService;

/**
 * UserController
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/user/me")
    public UserDto getLoggedUser(Principal principal){
        // if(Objects.isNull(principal)) throw new UnAuth
        String email = principal.getName();
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        return userService.getByEmail(userDto);
    }

    @GetMapping("/api/user/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth){
        if(Objects.isNull(auth)) return; // TODO: Throw BandRequest(인증안됨)
        new SecurityContextLogoutHandler().logout(request, response, auth);
    }

}