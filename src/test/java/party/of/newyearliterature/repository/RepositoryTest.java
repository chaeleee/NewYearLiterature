package party.of.newyearliterature.repository;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import party.of.newyearliterature.role.Role;
import party.of.newyearliterature.role.RoleBasicType;
import party.of.newyearliterature.user.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void 저장할_때_인자_엔티티와_리턴된_엔티티_참조가_같은가(){
        String email = "lee@gmail.com";
        String password = "123";
        String name = "leeyun";
        Role userRole = new Role(RoleBasicType.USER);

        User user = new User(email, password, name, userRole);

        User persist = entityManager.persist(user);

        assertTrue("같아야 한다",persist.equals(user));
    }

}