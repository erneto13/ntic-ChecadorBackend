package com.erneto13.ntic.service;

import com.erneto13.ntic.dto.UserUpdateDto;
import com.erneto13.ntic.jwt.repository.Token;
import com.erneto13.ntic.jwt.repository.TokenRepository;
import com.erneto13.ntic.model.Role;
import com.erneto13.ntic.model.User;
import com.erneto13.ntic.repository.RoleRepository;
import com.erneto13.ntic.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       TokenRepository tokenRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Integer id) {
        Optional<User> userOpt = getUserById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            List<Token> tokens = tokenRepository.findAllByUser(user);
            tokenRepository.deleteAll(tokens);

            userRepository.delete(user);
        }
    }

    @Transactional
    public User updateUser(Integer id, UserUpdateDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setUsername(userDto.getUsername());

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        if (userDto.getRole() != null) {
            Role role = roleRepository.findByName(Role.ERole.valueOf(userDto.getRole()))
                    .orElseThrow(() -> new RuntimeException("Role not found: " + userDto.getRole()));
            existingUser.setRole(role);
        }

        return userRepository.save(existingUser);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
