package party.of.newyearliterature.user;

import java.util.Objects;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.exception.BadRequestException;

/**
 * UserServiceImpl
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserDto signUpDto(UserDto userDto) {
        User user = signUp(userDto);
        return UserMapper.map(user);
    }

    public User signUp(UserDto userDto){
        validate(userDto);
        User user = UserMapper.map(userDto);
        return userRepository.save(user);
    }

    @Override
    public UserDto remove(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto getByEmail(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto getByNickname(UserDto userDto) {
        return null;
    }

    private void validate(UserDto userDto){
        if(Objects.isNull(userDto.getEmail())
            || Objects.isNull(userDto.getName())
            || Objects.isNull(userDto.getPassword())
        ){
            throw new BadRequestException("유저 입력값이 잘못되었습니다.");
        }

        if(userDto.getEmail().isBlank()
            || userDto.getName().isBlank()
            || userDto.getPassword().isBlank()
        ){
            throw new BadRequestException("유저 입력값이 공란입니다.");
        }
    }

}