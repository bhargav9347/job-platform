package com.jobportal.service;

import com.jobportal.model.User;
import com.jobportal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String uploadDir = "uploads/resumes/";

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateProfile(User updatedUser, MultipartFile resume) throws IOException {
        User existingUser = userRepository.findById(updatedUser.getId()).orElseThrow();
        
        existingUser.setName(updatedUser.getName());
        existingUser.setSkills(updatedUser.getSkills());
        existingUser.setExperience(updatedUser.getExperience());
        existingUser.setEducation(updatedUser.getEducation());

        if (resume != null && !resume.isEmpty()) {
            Path root = Paths.get(uploadDir);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            String filename = UUID.randomUUID().toString() + "_" + resume.getOriginalFilename();
            Files.copy(resume.getInputStream(), root.resolve(filename));
            existingUser.setResumePath(filename);
        }

        return userRepository.save(existingUser);
    }
}
