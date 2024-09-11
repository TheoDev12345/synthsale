package com.doranco.synthsale.service.impl;

import com.doranco.synthsale.model.Role;
import com.doranco.synthsale.model.User;
import com.doranco.synthsale.repository.RoleRepository;
import com.doranco.synthsale.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        User user = new User("testUser", "test@example.com", "Password123!");
        Role userRole = new Role("USER");

        when(passwordEncoder.encode("Password123!")).thenReturn("encodedPassword123");
        when(roleRepository.findByName("USER")).thenReturn(userRole);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        userService.registerUser(user);

        // Assert
        assertEquals("encodedPassword123", user.getPassword());
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(userRole));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_RoleNotFound() {
        // Arrange
        User user = new User("testUser", "test@example.com", "Password123!");

        when(passwordEncoder.encode("Password123!")).thenReturn("encodedPassword123");
        when(roleRepository.findByName("USER")).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.registerUser(user));
        assertEquals("Role USER not found in the database", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUser_UserAlreadyHasRoles() {
        // Arrange
        Role existingRole = new Role("EXISTING_ROLE");
        User user = new User("testUser", "test@example.com", "Password123!");
        user.setRoles(Set.of(existingRole));

        when(passwordEncoder.encode("Password123!")).thenReturn("encodedPassword123");

        // Act
        userService.registerUser(user);

        // Assert
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(existingRole));
        verify(userRepository, times(1)).save(user);
    }
}
