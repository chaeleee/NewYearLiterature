package party.of.newyearliterature.work;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import party.of.newyearliterature.user.UserDto;

/**
 * WorkCreateDto
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkCreateDto {

    private Long id;
    private String article;
    private String author;
    @JsonProperty("user")
    private UserDto userDto;
    private String award;
    private Long createdAt;
}