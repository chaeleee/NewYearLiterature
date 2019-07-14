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
        user.setNickname(dto.getNickname());
        return user;
    }

	public static UserDto map(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNickname(user.getNickname());
        if(!Objects.isNull(user.getCreatedAt())){
            dto.setCreatedAt(user.getCreatedAt().toEpochSecond(ZoneOffset.UTC));
        }
        return dto;
	}
}