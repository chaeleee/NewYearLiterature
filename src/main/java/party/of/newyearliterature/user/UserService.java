package party.of.newyearliterature.user;

/**
 * UserService
 */
public interface UserService {

    UserDto signUpDto(UserDto userDto);
    User signUp(UserDto userDto);
    UserDto remove(UserDto userDto);
    UserDto getByEmail(UserDto userDto);
    UserDto getByNickname(UserDto userDto);

}