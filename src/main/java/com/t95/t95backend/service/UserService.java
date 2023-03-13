package com.t95.t95backend.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.t95.t95backend.entity.User;
import com.t95.t95backend.repository.UserRepository;
import com.t95.t95backend.utils.encryption.SHA256Utils;

@Service
public class UserService {

    private final UserRepository userRepository;
    private SHA256Utils sha256Utils;

    @Autowired
    public UserService(UserRepository userRepository, SHA256Utils sha256Utils) {
        super();
        this.userRepository = userRepository;
        this.sha256Utils = sha256Utils;
    }

    public Optional<User> getUser(Long userId) { return userRepository.findById(userId); }
    public Optional<User> getUserByName(String username) { return userRepository.findUserByName(username); }

    public User addNewUser(User user) {
        Optional<User> userEmail = userRepository.findUserByEmail(user.getEmail());
        if (userEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        
        Optional<User> userName = userRepository.findUserByName(user.getName());
        if (userName.isPresent()) {
            throw new IllegalStateException("name taken");
        }
        // sha256 hashed password and salt
    	try {
    		String hashedPassword = sha256Utils.hash((String) user.getPassword());    		
    		user.setPassword(hashedPassword);
    	} catch (Exception e) {
    		throw new IllegalStateException("password illegal");
    	}
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("user with id: " + userId + " does not exist!");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, String name, String password, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("user with id: " + userId + " does not exist!"));

        if(name != null && name.length() > 0 && !Objects.equals(user.getName(), name)) {
        	Optional<User> userName = userRepository.findUserByName(name);
            if (userName.isPresent()) {
                throw new IllegalStateException("name taken");
            }
            user.setName(name);
        }

        if(password != null && password.length() > 0 && !Objects.equals(user.getPassword(), password)) {
        	// sha256 hashed password and salt
        	try {
        		String hashedPassword = sha256Utils.hash((String) password);    		
        		user.setPassword(hashedPassword);
        	} catch (Exception e) {
        		throw new IllegalStateException("password illegal");
        	}
        }

        if(email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            Optional<User> userOptional = userRepository.findUserByEmail(email);
            if(userOptional.isPresent()) {
                throw new IllegalStateException("email is taken!");
            }
            user.setEmail(email);
        }
    }

    public User findByNameAndPassword(String name, String password){
        return userRepository.findByNameAndPassword(name, password);
    }
}
