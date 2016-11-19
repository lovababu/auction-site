package com.sapient.auction.security;

import com.sapient.auction.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class CustomUserDetailsService/* implements UserDetailsService*/ {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //@Override
    /*public CurrentSessionUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
        return new CurrentSessionUser(user);
    }*/
}
