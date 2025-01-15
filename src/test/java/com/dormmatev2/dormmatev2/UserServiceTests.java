// package com.dormmatev2.dormmatev2;

// import com.dormmatev2.dormmatev2.model.User;
// import com.dormmatev2.dormmatev2.repositories.UserRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import com.dormmatev2.dormmatev2.service.UserService;

// import java.util.Arrays;
// import java.util.Collections;
// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// class UserServiceTest {

//     @Mock
//     private UserRepository userRepository;

//     @Mock
//     private PasswordEncoder passwordEncoder;

//     @InjectMocks
//     private UserService userService;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void saveUser_ValidUser_ReturnsSavedUser() {
//         // Arrange
//         User user = new User();
//         user.setUsername("testuser");
//         user.setEmail("test@example.com");
//         user.setPassword("password");
//         user.setRole("tenant");

//         when(userRepository.findByUsername(anyString())).thenReturn(null);
//         when(userRepository.findByEmail(anyString())).thenReturn(null);
//         when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
//         when(userRepository.save(any(User.class))).thenReturn(user);

//         // Act
//         User savedUser = userService.saveUser(user);

//         // Assert
//         assertNotNull(savedUser);
//         assertEquals("hashedPassword", savedUser.getPassword());
//         verify(userRepository, times(1)).save(user);
//         verify(passwordEncoder, times(1)).encode("password");
//     }

//     @Test
//     void saveUser_DuplicateUsername_ThrowsException() {
//         // Arrange
//         User user = new User();
//         user.setUsername("testuser");
//         user.setEmail("test@example.com");
//         user.setPassword("password");

//         when(userRepository.findByUsername(anyString())).thenReturn(new User());

//         // Act & Assert
//         assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
//         verify(userRepository, never()).save(any(User.class));
//     }

//     @Test
//     void saveUser_DuplicateEmail_ThrowsException() {
//         // Arrange
//         User user = new User();
//         user.setUsername("testuser");
//         user.setEmail("test@example.com");
//         user.setPassword("password");

//         when(userRepository.findByUsername(anyString())).thenReturn(null);
//         when(userRepository.findByEmail(anyString())).thenReturn(new User());

//         // Act & Assert
//         assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
//         verify(userRepository, never()).save(any(User.class));
//     }

//     @Test
//     void findUserByUsername_UserExists_ReturnsUser() {
//         // Arrange
//         String username = "testuser";
//         User expectedUser = new User();
//         expectedUser.setUsername(username);

//         when(userRepository.findByUsername(username)).thenReturn(expectedUser);

//         // Act
//         User foundUser = userService.findUserByUsername(username);

//         // Assert
//         assertNotNull(foundUser);
//         assertEquals(expectedUser.getUsername(), foundUser.getUsername());
//     }

//     @Test
//     void findUserByUsername_UserDoesNotExist_ReturnsNull() {
//         // Arrange
//         String username = "nonexistentuser";

//         when(userRepository.findByUsername(username)).thenReturn(null);

//         // Act
//         User foundUser = userService.findUserByUsername(username);

//         // Assert
//         assertNull(foundUser);
//     }

//     @Test
//     void findUserByEmail_UserExists_ReturnsUser() {
//         // Arrange
//         String email = "test@example.com";
//         User expectedUser = new User();
//         expectedUser.setEmail(email);

//         when(userRepository.findByEmail(email)).thenReturn(expectedUser);

//         // Act
//         User foundUser = userService.findUserByEmail(email);

//         // Assert
//         assertNotNull(foundUser);
//         assertEquals(expectedUser.getEmail(), foundUser.getEmail());
//     }

//     @Test
//     void findUserByEmail_UserDoesNotExist_ReturnsNull() {
//         // Arrange
//         String email = "nonexistent@example.com";

//         when(userRepository.findByEmail(email)).thenReturn(null);

//         // Act
//         User foundUser = userService.findUserByEmail(email);

//         // Assert
//         assertNull(foundUser);
//     }

//     @Test
//     void findAllUsers_NoUsers_ReturnsEmptyList() {
//         // Arrange
//         when(userRepository.findAll()).thenReturn(Collections.emptyList());

//         // Act
//         List<User> users = userService.findAllUsers();

//         // Assert
//         assertNotNull(users);
//         assertTrue(users.isEmpty());
//     }

//     @Test
//     void findAllUsers_MultipleUsers_ReturnsListOfUsers() {
//         // Arrange
//         List<User> userList = Arrays.asList(new User(), new User());
//         when(userRepository.findAll()).thenReturn(userList);

//         // Act
//         List<User> users = userService.findAllUsers();

//         // Assert
//         assertNotNull(users);
//         assertEquals(2, users.size());
//     }

//     @Test
//     void findUserById_UserExists_ReturnsOptionalWithValue() {
//         // Arrange
//         Long userId = 1L;
//         User expectedUser = new User();
//         expectedUser.setUserId(userId);

//         when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

//         // Act
//         Optional<User> foundUser = userService.findUserById(userId);

//         // Assert
//         assertTrue(foundUser.isPresent());
//         assertEquals(expectedUser.getUserId(), foundUser.get().getUserId());
//     }

//     @Test
//     void findUserById_UserDoesNotExist_ReturnsEmptyOptional() {
//         // Arrange
//         Long userId = 1L;

//         when(userRepository.findById(userId)).thenReturn(Optional.empty());

//         // Act
//         Optional<User> foundUser = userService.findUserById(userId);

//         // Assert
//         assertFalse(foundUser.isPresent());
//     }

//     @Test
//     void deleteUser_UserExists_DeletesUser() {
//         // Arrange
//         Long userId = 1L;

//         // Act
//         userService.deleteUser(userId);

//         // Assert
//         verify(userRepository, times(1)).deleteById(userId);
//     }

//     @Test
//     void updateUser_UserExists_ReturnsUpdatedUser() {
//         // Arrange
//         Long userId = 1L;
//         User existingUser = new User();
//         existingUser.setUserId(userId);
//         existingUser.setUsername("oldUsername");
//         existingUser.setEmail("old@example.com");
//         existingUser.setPassword("oldHashedPassword");
//         existingUser.setRole("tenant");

//         User userDetails = new User();
//         userDetails.setUsername("newUsername");
//         userDetails.setEmail("new@example.com");
//         userDetails.setPassword("newPassword");
//         userDetails.setRole("admin");

//         when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
//         when(userRepository.findByUsername(userDetails.getUsername())).thenReturn(null);
//         when(userRepository.findByEmail(userDetails.getEmail())).thenReturn(null);
//         when(passwordEncoder.encode(userDetails.getPassword())).thenReturn("newHashedPassword");
//         when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

//         // Act
//         User updatedUser = userService.updateUser(userId, userDetails);

//         // Assert
//         assertNotNull(updatedUser);
//         assertEquals("newUsername", updatedUser.getUsername());
//         assertEquals("new@example.com", updatedUser.getEmail());
//         assertEquals("newHashedPassword", updatedUser.getPassword());
//         assertEquals("admin", updatedUser.getRole());
//         verify(userRepository).save(existingUser);
//     }

//     @Test
//     void updateUser_UserDoesNotExist_ThrowsException() {
//         // Arrange
//         Long userId = 1L;
//         User userDetails = new User();
//         userDetails.setUsername("newUsername");

//         when(userRepository.findById(userId)).thenReturn(Optional.empty());

//         // Act & Assert
//         assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userId, userDetails));
//         verify(userRepository, never()).save(any(User.class));
//     }

//     @Test
//     void updateUser_DuplicateUsername_ThrowsException() {
//         // Arrange
//         Long userId = 1L;
//         User existingUser = new User();
//         existingUser.setUserId(userId);
//         existingUser.setUsername("oldUsername");
//         existingUser.setEmail("old@example.com");

//         User userDetails = new User();
//         userDetails.setUsername("existingUsername");
//         userDetails.setEmail("new@example.com");

//         when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
//         when(userRepository.findByUsername(userDetails.getUsername())).thenReturn(new User()); // Simulate another user with the same username

//         // Act & Assert
//         assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userId, userDetails));
//         verify(userRepository, never()).save(any(User.class));
//     }

//     @Test
//     void updateUser_DuplicateEmail_ThrowsException() {
//         // Arrange
//         Long userId = 1L;
//         User existingUser = new User();
//         existingUser.setUserId(userId);
//         existingUser.setUsername("oldUsername");
//         existingUser.setEmail("old@example.com");

//         User userDetails = new User();
//         userDetails.setUsername("newUsername");
//         userDetails.setEmail("existing@example.com");

//         when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
//         when(userRepository.findByUsername(userDetails.getUsername())).thenReturn(null);
//         when(userRepository.findByEmail(userDetails.getEmail())).thenReturn(new User()); // Simulate another user with the same email

//         // Act & Assert
//         assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userId, userDetails));
//         verify(userRepository, never()).save(any(User.class));
//     }
//     @Test
//     void existsByUsername_UserExists_ReturnsTrue() {
//         // Arrange
//         String username = "existinguser";

//         when(userRepository.findByUsername(username)).thenReturn(new User());

//         // Act
//         boolean exists = userService.existsByUsername(username);

//         // Assert
//         assertTrue(exists);
//     }

//     @Test
//     void existsByUsername_UserDoesNotExist_ReturnsFalse() {
//         // Arrange
//         String username = "nonexistentuser";

//         when(userRepository.findByUsername(username)).thenReturn(null);

//         // Act
//         boolean exists = userService.existsByUsername(username);

//         // Assert
//         assertFalse(exists);
//     }

//     @Test
//     void existsByEmail_UserExists_ReturnsTrue() {
//         // Arrange
//         String email = "existing@example.com";

//         when(userRepository.findByEmail(email)).thenReturn(new User());

//         // Act
//         boolean exists = userService.existsByEmail(email);

//         // Assert
//         assertTrue(exists);
//     }

//     @Test
//     void existsByEmail_UserDoesNotExist_ReturnsFalse() {
//         // Arrange
//         String email = "nonexistent@example.com";

//         when(userRepository.findByEmail(email)).thenReturn(null);

//         // Act
//         boolean exists = userService.existsByEmail(email);

//         // Assert
//         assertFalse(exists);
//     }

// }