package com.agan.onlinebuysellplatform.service;

import com.agan.onlinebuysellplatform.model.User;
import com.agan.onlinebuysellplatform.model.enums.Role;
import com.agan.onlinebuysellplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean createUser(User user) {
        String email = user.getEmail();

        if (userRepository.findByEmail(email) != null) return false;

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);

        log.info("Saving new User with email: {}", email);
        userRepository.save(user);

        return true;
    }

}