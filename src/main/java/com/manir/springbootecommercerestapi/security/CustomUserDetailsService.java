package com.manir.springbootecommercerestapi.security;

import com.manir.springbootecommercerestapi.model.Role;
import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        User user =  userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail)
                                   .orElseThrow(
                                           () -> new UsernameNotFoundException("User not found with username or email: " + userNameOrEmail)
                                   );
        return new org.springframework.security.core.userdetails.User(
                                    user.getEmail(),
                                    user.getPassword(),
                                    mapRolesToAuthorities(user.getRoles())
                            );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList());
    }
}
