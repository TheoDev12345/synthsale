package com.doranco.synthsale.service.impl;

import com.doranco.synthsale.model.Role;
import com.doranco.synthsale.model.User;
import com.doranco.synthsale.repository.RoleRepository;
import com.doranco.synthsale.repository.UserRepository;
import com.doranco.synthsale.service.UserService;
import com.doranco.synthsale.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    @Override
    public void registerUser(User user) {

        // Validation des noms d'utilisateur
        if (!isValidUsername(user.getUsername()) || !InputValidator.isValidInput(user.getUsername())) {
            throw new IllegalArgumentException("Nom d'utilisateur invalide.");
        }

        // Validation des e-mails
        if (!isValidEmail(user.getEmail()) || !InputValidator.isValidInput(user.getEmail())) {
            throw new IllegalArgumentException("Email invalide.");
        }

        // Validation des mots de passe
        if (!isValidPassword(user.getPassword()) || !InputValidator.isValidInput(user.getPassword())) {
            throw new IllegalArgumentException("Mot de passe invalide.");
        }

        // Cryptage du mot de passe avant de sauvegarder l'utilisateur
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        System.out.println("Mot de passe encodé pour l'utilisateur : " + user.getUsername());

        // Assigner le rôle USER par défaut
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            System.out.println("Aucun rôle spécifié pour l'utilisateur, attribution du rôle par défaut USER");

            Role defaultRole = roleRepository.findByName("USER");
            if (defaultRole != null) {
                user.setRoles(Set.of(defaultRole)); // s'assurer que User possède un champ roles de type Set<Role>
                System.out.println("Rôle USER attribué à l'utilisateur : " + user.getUsername());
            } else {
                System.out.println("Erreur : Rôle USER introuvable dans la base de données pour l'utilisateur : " + user.getUsername());
                throw new RuntimeException("Role USER non trouvé dans la base de données");
            }
        } else {
            System.out.println("Rôles déjà présents pour l'utilisateur : " + user.getRoles());
        }

        userRepository.save(user);
        System.out.println("Utilisateur enregistré avec succès : " + user.getUsername());


    }

    // Méthode pour valider les noms d'utilisateur
    private boolean isValidUsername(String username) {
        // Regex pour un nom d'utilisateur de 3 à 16 caractères, lettres, chiffres, underscore ou tiret
        String usernamePattern = "^[a-zA-Z0-9_-]{3,16}$";
        return username != null && username.matches(usernamePattern);
    }

    // Méthode pour valider les e-mails
    private boolean isValidEmail(String email) {
        // Regex pour un email valide
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailPattern);
    }

    // Méthode pour valider les mots de passe
    private boolean isValidPassword(String password) {
        // Regex pour un mot de passe de 8 caractères minimum avec au moins une majuscule, une minuscule, un chiffre et un caractère spécial
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
        return password != null && password.matches(passwordPattern);
    }



    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        User user = findUserByUsername(username);
        if (user != null) {
            // Vérification du mot de passe avec BCrypt
            boolean isPasswordValid = passwordEncoder.matches(password, user.getPassword());
            if (isPasswordValid) {
                System.out.println("Authentication réussi pour utilisateur : " + username);
            } else {
                System.out.println("Authentication refusé pour utilisateur : " + username);
            }
            return isPasswordValid;
        }
        System.out.println("Utilisateur non trouvé avec nom d'utilisateur : " + username);
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec nom d'utilisateur : " + username);
        }
        return user;
    }
}
