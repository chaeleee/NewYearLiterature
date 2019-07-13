package party.of.newyearliterature.work;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * WorkDto
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkDto {

    private Long id;
    private String article;
    private String author;
    private Long userId;
    private String userEmail;
    private String award;
    private Long createdAt;
    
}