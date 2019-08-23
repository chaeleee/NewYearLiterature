package party.of.newyearliterature.user;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * UserDto
 */
@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private String password;
    private Long createdAt;
    private Long updatedAt;

    public UserDto(){}

    @Builder
    public UserDto(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserDto)) {
            return false;
        }
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) 
            && Objects.equals(email, userDto.email) 
            && Objects.equals(name, userDto.name) 
            && Objects.equals(createdAt, userDto.createdAt) 
            && Objects.equals(updatedAt, userDto.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, createdAt, updatedAt);
    }


}