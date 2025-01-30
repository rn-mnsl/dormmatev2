package com.dormmatev2.dormmatev2.controller;

import com.dormmatev2.dormmatev2.model.User;
import com.dormmatev2.dormmatev2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("username", user.getUsername());
            response.put("role", user.getRole());

            // Check if the user has a unit and add unitId to the response if available
            if (user.getUnit() != null) {
                response.put("unitId", user.getUnit().getUnitId());
            }
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

       @PostMapping
    public ResponseEntity<User> createUser(@RequestParam(required=false) Long unitId, @RequestBody User user) {
        try {
             User savedUser = userService.saveUser(user, unitId);
              return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
       } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
     }

      @GetMapping
     public ResponseEntity<List<User>> getAllUsers() {
          List<User> users = userService.findAllUsers();
          return new ResponseEntity<>(users, HttpStatus.OK);
       }

      @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
          Optional<User> user = userService.findUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
      }

   @GetMapping("/byUsername/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
          User user = userService.findUserByUsername(username);
         if (user != null) {
              return new ResponseEntity<>(user, HttpStatus.OK);
          } else {
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
      }

    @GetMapping("/byEmail/{email}")
     public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
          User user = userService.findUserByEmail(email);
          if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
  }

        @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
         try {
           User updatedUser = userService.updateUser(id, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
             return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
     }
      @DeleteMapping("/{id}")
       public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        try {
             userService.deleteUser(id);
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
          }
       }
}