package party.of.newyearliterature.like;

/**
 * LikeService
 */
public interface LikeService {

    Like save(Like like);
    Like delete(long id);

}