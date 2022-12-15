package com.t95.t95backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.security.auth.message.AuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.t95.t95backend.entity.User;
import com.t95.t95backend.returnBean.ReturnBooleanResultBean;
import com.t95.t95backend.returnBean.ReturnUserInfo;
import com.t95.t95backend.service.UserService;
import com.t95.t95backend.utils.encryption.JwtTokenUtils;
import com.t95.t95backend.utils.encryption.SHA256Utils;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private final UserService userService;
    private JwtTokenUtils jwtTokenUtils;
    private SHA256Utils sha256Utils;

    @Autowired
    public UserController(UserService userService, SHA256Utils sha256Utils, JwtTokenUtils jwtTokenUtils) {
        super();
        this.userService = userService;
        this.sha256Utils=sha256Utils;
        this.jwtTokenUtils=jwtTokenUtils;
    }

    //get user by id
    @GetMapping
    public ResponseEntity getUser(@RequestHeader("Authorization") String authorization) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		Optional<User> user = userService.getUser(userInfo.getId());        
    		if(user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);   
    		return ResponseEntity.status(HttpStatus.OK).body(user);    		
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    	
    }

    //sign up
    @PostMapping("/signup")
    public ResponseEntity registerNewUser(@RequestBody User newUser) {
        try {
        	User user = userService.addNewUser(newUser);                	
        	//create sessions
        	ReturnUserInfo sessions = new ReturnUserInfo(user.getName(), user.getId());        	
        	//create JWT token
        	String jwtToken = jwtTokenUtils.generateToken(sessions);        	
        	//add JWT Token to sessions
        	sessions.setJwt(jwtToken);            
            return ResponseEntity.status(HttpStatus.OK).body(sessions);
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");       	
        }    	
    }
    
    //find user name duplicate
    @GetMapping(path = "findDuplicate/{username}")
    public ResponseEntity findDuplicateUsername(@PathVariable(required = true) String username) throws Exception {
        ReturnBooleanResultBean returnBooleanResultBean = new ReturnBooleanResultBean();
        Optional<User> user = userService.getUserByName(username);      
        if (user.isPresent()) {
            returnBooleanResultBean.setResult(true);
        } else {
            returnBooleanResultBean.setResult(false);
        }
        return ResponseEntity.status(HttpStatus.OK).body(returnBooleanResultBean);		
    }

//    //delete user
//    @DeleteMapping(path = "{userId}")
//    public ResponseEntity deleteUser(@RequestHeader("Authorization") String authorization, @PathVariable("userId") Long userId) {
//		try {
//			//JWT: verify and parse JWT token includes user info
//			ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
//			
//			userService.deleteUser(userId);
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("\"success deleted user\"");			
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//		}
//    	
//    }

    //edit user profile
    @PutMapping
    public ResponseEntity updateUser(@RequestHeader("Authorization") String authorization,
    		@RequestBody(required = true) User user) {
		try {
			//JWT: verify and parse JWT token includes user info
			ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
						
			userService.updateUser(userInfo.getId(), user.getName(), user.getPassword(), user.getEmail());
			return ResponseEntity.status(HttpStatus.OK).body("\"success updated user\"");
		} catch (AuthException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
        
    }
}
