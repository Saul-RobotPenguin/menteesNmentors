package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoginRequest;
import com.example.demo.models.User;
import com.example.demo.services.UserServices;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User newUser) {
        Optional<User> existingUser = userServices.findByEmail(newUser.getEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }

        Optional<User> registeredUser = userServices.saveUser(newUser);

        if (registeredUser.isPresent()) {
            return ResponseEntity.ok().body(registeredUser);
        } else {
            return ResponseEntity.badRequest().body("An error has occurred during registration.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userServices.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user.isPresent()) {
            return ResponseEntity.ok().body("granted");
        } else {
            return ResponseEntity.badRequest().body("Email or Password is Incorrect");
        }
    }

    @GetMapping("/users")
    public List<User> getUser() {
        return userServices.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userServices.getUserById(id);
    }

    @PostMapping("/{mentorId}/add-mentee/{menteeId}")
    public Optional<User> addMentee(@PathVariable Long mentorId, @PathVariable Long menteeId) {
        return userServices.addMentee(mentorId, menteeId);
    }

    @DeleteMapping("/{mentorId}/remove-mentee/{menteeId}")
    public Optional<User> removeMentee(@PathVariable Long mentorId, @PathVariable Long menteeId) {
        return userServices.removeMentee(mentorId, menteeId);
    }

    @GetMapping("/{mentorId}/mentees")
    public ResponseEntity<?> getMentees(@PathVariable Long mentorId) {
        List<User> mentees = userServices.getAllMentees(mentorId);

        if (!mentees.isEmpty()) {
            return ResponseEntity.ok().body(mentees);
        } else {
            return ResponseEntity.badRequest().body("User has no mentees");
        }
    }

    @GetMapping("/{menteeId}/mentors")
    public ResponseEntity<?> getMentors(@PathVariable Long menteeId) {
        List<User> mentors = userServices.getAllMentors(menteeId);

        if (!mentors.isEmpty()) {
            return ResponseEntity.ok().body(mentors);
        } else {
            return ResponseEntity.badRequest().body("User has no mentors");
        }
    }

}
