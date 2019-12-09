package party.of.newyearliterature.like;

/**
 * LikeCreateDto
 */
public class LikeCreateDto {

    private String username;
    private Long workId;

    public LikeCreateDto(String username, Long workId){
        this.username = username;
        this.workId = workId;
    }
}