package party.of.newyearliterature.work;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * WorkCreateLoggedDto
 */
@Getter @Setter @AllArgsConstructor @Builder @NoArgsConstructor
public class WorkCreateLoggedDto {

    private String article;
    private String author;
    private String userEmail;

}