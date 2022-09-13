package com.t95.t95backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.t95.t95backend.entity.User;
import com.t95.t95backend.service.UserService;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    //get list of all users
    @GetMapping
    public ResponseEntity getUsers() {
    	List<User> users = userService.getUsers();        
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    //sign up
    @PostMapping
    public ResponseEntity registerNewUser(@RequestBody User user) {
    	userService.addNewUser(user);
    	return ResponseEntity.status(HttpStatus.CREATED).body("success created user");
    }

    //delete user
    @DeleteMapping(path = "{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success deleted user");
    }

    //edit user profile
    @PutMapping(path = "{userId}")
    public ResponseEntity updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String email) {
        userService.updateUser(userId, name, password, email);
        return ResponseEntity.status(HttpStatus.OK).body("success updated user");
        
    }
}
