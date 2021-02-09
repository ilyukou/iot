package by.grsu.iot.api.security;

import by.grsu.iot.api.service.interf.UserService;
import by.grsu.iot.model.sql.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserService service;

    public CustomUserDetailsService(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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

        user.getRoles().forEach(role -> grantedAuthority.add(new SimpleGrantedAuthority(role.getRole().getValue())));

        return grantedAuthority;
    }


}