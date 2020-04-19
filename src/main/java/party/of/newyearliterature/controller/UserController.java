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
import party.of.newyearliterature.exception.BadRequestException;
import party.of.newyearliterature.exception.NotFoundException;
import party.of.newyearliterature.user.UserDto;
import party.of.newyearliterature.user.UserService;
import springfox.documentation.annotations.ApiIgnore;

/**
 * UserController
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/user/me")
    public UserDto getLoggedUser(@ApiIgnore Principal principal){
        if(Objects.isNull(principal)) throw new NotFoundException("로그인 정보가 없습니다");
        String email = principal.getName();
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        return userService.getByEmail(userDto);
    }

    @GetMapping("/api/user/logout")
    public void logout(@ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response, @ApiIgnore Authentication auth){
        if(Objects.isNull(auth)) throw new BadRequestException("로그인 정보가 없습니다");
        new SecurityContextLogoutHandler().logout(request, response, auth);
    }

}