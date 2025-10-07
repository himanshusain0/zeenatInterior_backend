package com.zeenatInterior.interiordesign.security;




import com.zeenatInterior.interiordesign.entity.User;
import com.zeenatInterior.interiordesign.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> auth = List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                u.getEmail(), u.getPassword(), auth
        );
    }
}