package com.t95.t95backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.t95.t95backend.bean.LoginBean;
import com.t95.t95backend.entity.User;
import com.t95.t95backend.returnBean.ReturnUserInfo;
import com.t95.t95backend.service.UserService;
import com.t95.t95backend.utils.encryption.JwtTokenUtils;
import com.t95.t95backend.utils.encryption.SHA256Utils;

@RestController
@RequestMapping("")
public class LoginController {
    private UserService userService;
    private SHA256Utils sha256Utils;
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    public LoginController(UserService userService, SHA256Utils sha256Utils, JwtTokenUtils jwtTokenUtils) {
        this.userService = userService;
        this.sha256Utils=sha256Utils;
        this.jwtTokenUtils=jwtTokenUtils;
    }

    @PostMapping("api/login")
    public ResponseEntity login(@RequestBody LoginBean loginBean) {    	
    	// sha256 hashed password and salt
    	String hashedPassword = "";
    	try {
    		hashedPassword = sha256Utils.hash((String) loginBean.getPassword());
    		//System.out.println(hashedPassword);
    		//77722f04d569b3021df77e8bb2e6b1337813be5c7993d3fef6b172a91d8f1f80
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
    	}
    	
    	//Verify name and password
        User user = userService.findByNameAndPassword(loginBean.getName(), hashedPassword);

        if(user != null) {
        	
        	//create sessions
        	ReturnUserInfo sessions = new ReturnUserInfo(user.getName(), user.getId());
        	
        	//create JWT token
        	String jwtToken = jwtTokenUtils.generateToken(sessions);
        	
        	//add JWT Token to sessions
        	sessions.setJwt(jwtToken);          
            
            return ResponseEntity.status(HttpStatus.OK).body(sessions);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong credentials");
    }

    @GetMapping(value = "/")
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok("Health Check: Server is OK.");
    }
}
