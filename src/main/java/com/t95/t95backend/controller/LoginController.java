package com.t95.t95backend.controller;

import com.t95.t95backend.bean.LoginBean;
import com.t95.t95backend.entity.User;
import com.t95.t95backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity login(@RequestBody LoginBean loginBean) {
//        System.out.println(loginBean.getName());
//        System.out.println(loginBean.getPassword());
        User user = userService.findByNameAndPassword(loginBean.getName(), loginBean.getPassword());
        System.out.println(user);
        if(user != null) {
            Map<String, String> sessions = new HashMap<>();
            sessions.put("id", user.getId().toString());
            sessions.put("name", user.getName());
            sessions.put("jwt", "123abc");
            return ResponseEntity.status(HttpStatus.OK).body(sessions);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong credentials");
    }
}
