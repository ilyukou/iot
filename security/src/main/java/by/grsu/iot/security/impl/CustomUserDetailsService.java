package by.grsu.iot.security.impl;

import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.interf.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository service;

    public CustomUserDetailsService(UserRepository service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getGrantedAuthorities(user)
        );
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {

        Collection<GrantedAuthority> grantedAuthority = new ArrayList<>();

        user.getRoles().forEach(role -> grantedAuthority.add(new SimpleGrantedAuthority(role.getRoleType().getValue())));

        return grantedAuthority;
    }


}
