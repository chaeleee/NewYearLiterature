package party.of.newyearliterature.user;

import java.time.ZoneOffset;
import java.util.Objects;

/**
 * UserMapper
 */
public class UserMapper {

    public static User map(UserDto dto){
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        return user;
    }

	public static UserDto map(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        if(!Objects.isNull(user.getCreatedAt())){
            dto.setCreatedAt(user.getCreatedAt().toEpochSecond(ZoneOffset.UTC));
        }
        dto.setRole(user.getRole());
        return dto;
	}
}