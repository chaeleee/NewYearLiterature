package party.of.newyearliterature.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import party.of.newyearliterature.user.User;
import party.of.newyearliterature.user.UserRepository;

/**
 * MyUserDetailsService
 */
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByEmail(email);
        User user = optUser.orElseThrow(
            ()->new UsernameNotFoundException("User not found with email: "+ email)
        );
        return new MyUserPrincipal(user);
	}

    
}