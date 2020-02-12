package party.of.newyearliterature.role;

import org.springframework.data.repository.CrudRepository;

/**
 * RoleRepository
 */
public interface RoleRepository extends CrudRepository<Role, Long>{

  Role findByName(String name);
    
}