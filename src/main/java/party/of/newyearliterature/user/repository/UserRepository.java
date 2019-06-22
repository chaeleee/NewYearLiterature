package party.of.newyearliterature.user.repository;

import org.springframework.data.repository.CrudRepository;

import party.of.newyearliterature.user.entities.User;

/**
 * UserRepository
 */
public interface UserRepository extends CrudRepository<User, Long>{

    
}