package com.dormmatev2.dormmatev2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.model.User;
import com.dormmatev2.dormmatev2.repositories.UnitRepository;
import com.dormmatev2.dormmatev2.repositories.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

       public User saveUser(User user, Long unitId) {
          if (userRepository.findByUsername(user.getUsername()) != null) {
                throw new IllegalArgumentException("Username already exists");
            }
        if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new IllegalArgumentException("Email already exists");
            }

          if(unitId != null) {
            Unit unit = unitRepository.findById(unitId)
                    .orElseThrow(() -> new IllegalArgumentException("Unit not found with id: " + unitId));
                user.setUnit(unit);
           }
            
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    

    public User findUserByUsername(String username) {
      return userRepository.findByUsername(username);
     }

    public User findUserByEmail(String email) {
          return userRepository.findByEmail(email);
    }
    
     public Optional<User> findUserById(Long id) {
         return userRepository.findById(id);
     }

     public List<User> findAllUsers() {
        return userRepository.findAll();
      }

      public void deleteUser(Long id) {
       userRepository.deleteById(id);
      }

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
            existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
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
        // In a real application, you should hash the password before comparing
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}