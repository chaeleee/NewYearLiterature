package party.of.newyearliterature.like;

/**
 * LikeService
 */
public interface LikeService {

    LikeDto like(LikeDto likeDto);
    LikeDto cancel(LikeDto likeDto);

}