package party.of.newyearliterature.work;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import party.of.newyearliterature.user.UserDto;

/**
 * WorkDto
 */
@Setter
@Getter
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkDto {

    private Long id;
    private String article;
    private String author;
    @JsonProperty("user")
    private UserDto userDto;
    private String award;
    private Long createdAt;
    

    public WorkDto(){}

    public WorkDto(String article, String author, UserDto userDto){
        this.article = article;
        this.author = author;
        this.userDto = userDto;
    }

}