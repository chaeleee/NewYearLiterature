package party.of.newyearliterature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import party.of.newyearliterature.role.Role;
import party.of.newyearliterature.role.RoleRepository;
import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserRepository;

/**
 * InitDB
 */
@Component
public class InitDB implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role adminRole = new Role("admin");
        adminRole = roleRepository.save(adminRole);
        
        Role userRole = new Role("user");
        userRole = roleRepository.save(userRole);

        User user1 = new User("user@of.com", passwordEncoder.encode("password"), "user1", userRole);
        userRepository.save(user1);

        User admin1 = new User("admin@of.com",  passwordEncoder.encode("admin"), "admin1", adminRole);
        userRepository.save(admin1);
    }

    
}