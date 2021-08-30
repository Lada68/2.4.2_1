package org.example.test1.security;

import org.example.test1.model.User;
import org.example.test1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        User user1 = userService.UserById(1);
        User user = userService.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        String password = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals((UsernamePasswordAuthenticationToken.class));
    }
}
