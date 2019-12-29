package party.of.newyearliterature.work;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

/**
 * WorkRepository
 */
public interface WorkRepository extends CrudRepository<Work, Long>{

	List<Work> findByAuthorContaining(String author, Sort sort);

    
}