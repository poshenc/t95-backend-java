package com.t95.t95backend.service;

import com.t95.t95backend.entity.UserAccount;
import com.t95.t95backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    public List<UserAccount> getUsers() { return userRepository.findAll(); }

    public void addNewUser(UserAccount user) {
        Optional<UserAccount> userOptional = userRepository.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("user with id: " + userId + " does not exist!");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, String firstName, String lastName, String email, Integer age) {
        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("user with id: " + userId + " does not exist!"));

        if(firstName != null && firstName.length() > 0 && !Objects.equals(user.getFirstName(), firstName)) {
            user.setFirstName(firstName);
        }

        if(lastName != null && lastName.length() > 0 && !Objects.equals(user.getLastName(), lastName)) {
            user.setLastName(lastName);
        }

        if(email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            Optional<UserAccount> userOptional = userRepository.findUserByEmail(email);
            if(userOptional.isPresent()) {
                throw new IllegalStateException("email is taken!");
            }
            user.setEmail(email);
        }

        if(age != null && age > 0 && !Objects.equals(user.getAge(), age)) {
            user.setAge(age);
        }
    }
}
