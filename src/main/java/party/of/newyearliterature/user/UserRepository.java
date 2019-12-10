package party.of.newyearliterature.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

/**
 * UserRepository
 */
public interface UserRepository extends CrudRepository<User, Long>{

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
	User findByName(String name);
}