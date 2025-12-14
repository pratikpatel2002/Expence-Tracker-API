package EXPENCE_TRACKER.service;

import EXPENCE_TRACKER.dto.UserDTO;
import EXPENCE_TRACKER.entity.User;
import EXPENCE_TRACKER.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.fromEntity(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.fromEntity(user);
    }

    @Transactional
    public UserDTO updateProfile(String id, String name, String email) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if email is already taken by another user
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already taken");
        }

        user.setName(name);
        user.setEmail(email);
        User updatedUser = userRepository.save(user);
        return UserDTO.fromEntity(updatedUser);
    }

    public List<UserDTO> getUsersByFamilyId(String familyId) {
        return userRepository.findByFamilyId(familyId).stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
}





