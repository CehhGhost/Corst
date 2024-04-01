package spring.crut.administration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spring.crut.administration.models.User;
import spring.crut.administration.services.CrutUserDetailsService;

import java.util.Collection;

@RequiredArgsConstructor
public class CrutUserDetails implements UserDetails {

    private final CrutUserDetailsService crutUserDetailsService;
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return crutUserDetailsService.getAuthorities(this.user);
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return this.user;
    }
}
