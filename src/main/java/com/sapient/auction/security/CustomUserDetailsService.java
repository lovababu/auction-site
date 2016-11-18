package com.sapient.auction.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sapient.auction.user.entity.User;
import com.sapient.auction.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CurrentSessionUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
        return new CurrentSessionUser(user);
    }
}
