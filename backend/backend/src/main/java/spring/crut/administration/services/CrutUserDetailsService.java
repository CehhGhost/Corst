package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.administration.models.Role;
import spring.crut.administration.models.User;
import spring.crut.administration.repositories.UsersRepository;
import spring.crut.administration.security.CrutUserDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrutUserDetailsService implements UserDetailsService {

private final UsersRepository usersRepository;
private final RolesService rolesService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(s);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CrutUserDetails(this, user.get());
    }
    @Transactional
    public List<SimpleGrantedAuthority> getAuthorities(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole() == null) {
            return Collections.singletonList(new SimpleGrantedAuthority("NO_AUTHORITIES"));
        }
        Role roleWithAuthorities = rolesService.getRoleWithAuthorities(user.getRole().getId());
        for (var authority : roleWithAuthorities.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return authorities;
    }
}
