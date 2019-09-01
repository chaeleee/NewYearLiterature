package party.of.newyearliterature.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 */
@RestController
public class UserController {

    @GetMapping("/api/user/secure")
    public String login(){
        return "secured";
    }
}