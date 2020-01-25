package party.of.newyearliterature.like;

import lombok.Getter;
import lombok.Setter;

/**
 * LikeCreateDto
 */
@Getter @Setter
public class LikeCreateDto {

    private String userEmail;
    private Long workId;
}