package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.crut.administration.models.User;
import spring.crut.administration.repositories.UsersRepository;
import spring.crut.administration.security.CrutUserDetails;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrutUserDetailsService implements UserDetailsService {

private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(s);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CrutUserDetails(user.get());
    }
}
