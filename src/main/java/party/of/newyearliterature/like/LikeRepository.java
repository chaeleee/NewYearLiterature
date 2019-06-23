package party.of.newyearliterature.like;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * LikeRepository
 */
@Repository
public interface LikeRepository extends CrudRepository<Like, Long>{

    
}