package party.of.newyearliterature.like;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * LikeRepository
 */
@Repository
public interface LikeRepository extends CrudRepository<Like, Long>{

	List<Like> findByWorkId(Long id);

	Like findByWorkIdAndUserId(Long id, Long id2);

        
}