package org.annill.taskmanager.service;

import lombok.AllArgsConstructor;
import org.annill.taskmanager.errors.UserNotFoundException;
import org.annill.taskmanager.errors.UserWithEmailExistsException;
import org.annill.taskmanager.errors.UserWithUserNameExistsException;
import org.annill.taskmanager.model.entity.User;
import org.annill.taskmanager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserWithUserNameExistsException();
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserWithEmailExistsException();
        }

        return save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

}

