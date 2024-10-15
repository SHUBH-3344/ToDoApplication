package com.sb.simpletask.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.sb.simpletask.dto.UserDto;
import com.sb.simpletask.security.CustomUserDetails;
import com.sb.simpletask.service.UserService;
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userDto));
    }

    @DeleteMapping("/deleteSelf")
    public ResponseEntity<?> deleteSelf(@AuthenticationPrincipal UserDetails userDetails) {
             Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
