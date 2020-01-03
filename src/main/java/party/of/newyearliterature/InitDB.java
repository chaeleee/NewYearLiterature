package party.of.newyearliterature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import party.of.newyearliterature.like.Like;
import party.of.newyearliterature.like.LikeRepository;
import party.of.newyearliterature.role.Role;
import party.of.newyearliterature.role.RoleRepository;
import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserRepository;
import party.of.newyearliterature.work.Work;
import party.of.newyearliterature.work.WorkRepository;

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

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private LikeRepository likeRepository;

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

        Work work1 = new Work("떡볶이 먹고 싶은 밤\n 밤밤밤", "불금", user1);
        Work work2 = new Work("떡볶이 먹고 싶은 밤", "불금", user1);
        Work work3 = new Work("떡볶이 먹고 싶은 밤", "불금", user1);
        Work work4 = new Work("떡볶이 먹고 싶은 밤", "불금", user1);
        Work work5 = new Work("떡볶이 먹고 싶은 밤", "불금", user1);
        workRepository.save(work1);
        workRepository.save(work2);
        workRepository.save(work3);
        workRepository.save(work4);
        workRepository.save(work5);

        Like like1 = new Like(user1, work1);
        likeRepository.save(like1);
    }

    
}