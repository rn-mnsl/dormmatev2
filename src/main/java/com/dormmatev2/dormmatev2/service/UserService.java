package com.dormmatev2.dormmatev2.service;

// Import necessary classes and interface

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.model.User;
import com.dormmatev2.dormmatev2.repositories.UnitRepository;
import com.dormmatev2.dormmatev2.repositories.UserRepository;

// This indicates that this is a service 
@Service
public class UserService {

    // this injects the User Repository to this class. 
    @Autowired
    private UserRepository userRepository;
    
    // this injects the Unit Repository to this class. 
    @Autowired
    private UnitRepository unitRepository;

        // method to save the new user into the database 
       public User saveUser(User user, Long unitId) {

        // get and check if the username already exists 
          if (userRepository.findByUsername(user.getUsername()) != null) {
                throw new IllegalArgumentException("Username already exists");
            }

        // get and check if the email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new IllegalArgumentException("Email already exists");
            }
        // if unit is not found 
          if(unitId != null) {
            Unit unit = unitRepository.findById(unitId)
                    .orElseThrow(() -> new IllegalArgumentException("Unit not found with id: " + unitId));
                user.setUnit(unit);
           }

           // save the user into the database 
        return userRepository.save(user);
    }
    
    // method to find the username of the tenant 
    public User findUserByUsername(String username) {

        // fetch the user and then display it. 
      return userRepository.findByUsername(username);
     }

     // method to find the user by the email 
    public User findUserByEmail(String email) {

        // fetch the user and then display it. 
          return userRepository.findByEmail(email);
    }
    
    // method to find the user by the ID 
     public Optional<User> findUserById(Long id) {

        // fetch the user and then display it. 
         return userRepository.findById(id);
     }

     // method to find all the users in the database 
     public List<User> findAllUsers() {

        // fetch and return all of the users in the database 
        return userRepository.findAll();
      }

    // method to delete a user in the database 
      public void deleteUser(Long id) {
       userRepository.deleteById(id);
      }

      // method to update an exisiting user in the database. 
   public User updateUser(Long id, User userDetails) {
     User existingUser = userRepository.findById(id)
              .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        // Update fields only if they are present in the request
        if (userDetails.getUsername() != null && !userDetails.getUsername().isEmpty()) {
            if (!existingUser.getUsername().equals(userDetails.getUsername()) && userRepository.findByUsername(userDetails.getUsername()) != null) {
               throw new IllegalArgumentException("Username already exists");
            }
           existingUser.setUsername(userDetails.getUsername());
         }
      if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty()) {
            if (!existingUser.getEmail().equals(userDetails.getEmail()) && userRepository.findByEmail(userDetails.getEmail()) != null) {
                throw new IllegalArgumentException("Email already exists");
            }
            existingUser.setEmail(userDetails.getEmail());
        }

        // Only update password if a new one is provided
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            existingUser.setPassword(userDetails.getPassword());
        }

        if (userDetails.getRole() != null && !userDetails.getRole().isEmpty()) {
            existingUser.setRole(userDetails.getRole());
        }
        
        if (userDetails.getName() != null && !userDetails.getName().isEmpty()) {
          existingUser.setName(userDetails.getName());
         }
         if (userDetails.getContactNumber() != null && !userDetails.getContactNumber().isEmpty()) {
            existingUser.setContactNumber(userDetails.getContactNumber());
         }

       if (userDetails.getMoveInDate() != null ) {
           existingUser.setMoveInDate(userDetails.getMoveInDate());
       }
       if (userDetails.getUnit() != null) {
              existingUser.setUnit(userDetails.getUnit());
        }
      return userRepository.save(existingUser);
    }

   public boolean existsByUsername(String username) {
       return userRepository.findByUsername(username) != null;
    }

   public boolean existsByEmail(String email) {
       return userRepository.findByEmail(email) != null;
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}