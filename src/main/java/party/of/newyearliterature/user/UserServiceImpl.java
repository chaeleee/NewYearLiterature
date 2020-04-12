package party.of.newyearliterature.user;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.exception.BadRequestException;
import party.of.newyearliterature.exception.NotFoundException;
import party.of.newyearliterature.role.Role;
import party.of.newyearliterature.role.RoleBasicType;
import party.of.newyearliterature.role.RoleRepository;

/**
 * UserServiceImpl
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDto signUpDto(UserDto userDto) {
        User user = signUp(userDto);
        return UserMapper.map(user);
    }

    @Override
    public User signUp(UserDto userDto){
        validate(userDto);
        if(Objects.isNull(userDto.getRole())){
            Role unLoggedUser = roleRepository.findByName(RoleBasicType.USER.getName());
            userDto.setRole(unLoggedUser);
        }
        User user = UserMapper.map(userDto);
        Role role = roleRepository.findByName(userDto.getRole().getName());
        if(Objects.isNull(userDto.getRole())){
            throw new BadRequestException("유저 권한이 지정되지 않았습니다.");
        }
        user.setRole(role);
        user.crpytPassword(bCryptPasswordEncoder);
        return userRepository.save(user);
    }

    @Override
    public UserDto remove(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto getByEmail(UserDto userDto) {
        Optional<User> opt = userRepository.findByEmail(userDto.getEmail());
        User user = opt.orElseThrow(
            ()-> new NotFoundException("유저 정보를 찾을 수 없습니다: "+ userDto.getEmail())
        );
        return UserMapper.map(user);
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

        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new BadRequestException("이미 가입된 이메일입니다.");
        }
    }

}