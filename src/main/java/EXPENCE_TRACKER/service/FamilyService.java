package EXPENCE_TRACKER.service;

import EXPENCE_TRACKER.dto.FamilyDTO;
import EXPENCE_TRACKER.dto.UserDTO;
import EXPENCE_TRACKER.entity.Family;
import EXPENCE_TRACKER.entity.User;
import EXPENCE_TRACKER.repository.FamilyRepository;
import EXPENCE_TRACKER.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

    @Transactional
    public FamilyDTO createFamily(String headId, String familyName) {
        System.out.println("FamilyService.createFamily - headId: " + headId + ", familyName: " + familyName);
        
        User head = userRepository.findById(headId)
                .orElseThrow(() -> {
                    System.err.println("User not found with ID: " + headId);
                    return new RuntimeException("User not found with ID: " + headId);
                });

        System.out.println("Found user: " + head.getEmail() + ", Role: " + head.getRole());

        Family family = new Family();
        family.setId(UUID.randomUUID().toString());
        family.setName(familyName);
        family.setHeadId(headId);
        family.setMembers(new ArrayList<>());
        family.getMembers().add(headId);

        Family savedFamily = familyRepository.save(family);
        System.out.println("Family saved with ID: " + savedFamily.getId());

        // Update user's familyId and role
        head.setFamilyId(savedFamily.getId());
        head.setRole(User.UserRole.HEAD);
        userRepository.save(head);
        System.out.println("User updated with familyId: " + savedFamily.getId());

        return FamilyDTO.fromEntity(savedFamily);
    }

    @Transactional
    public UserDTO addFamilyMember(String headId, String name, String email) {
        User head = userRepository.findById(headId)
                .orElseThrow(() -> new RuntimeException("Head user not found"));

        if (head.getRole() != User.UserRole.HEAD) {
            throw new RuntimeException("Only head of family can add members");
        }

        Family family = familyRepository.findById(head.getFamilyId())
                .orElseThrow(() -> new RuntimeException("Family not found"));

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User with this email already exists");
        }

        User newMember = new User();
        newMember.setId(UUID.randomUUID().toString());
        newMember.setName(name);
        newMember.setEmail(email);
        newMember.setPassword(null); // No password for family members
        newMember.setRole(User.UserRole.MEMBER);
        newMember.setFamilyId(family.getId());

        User savedMember = userRepository.save(newMember);

        // Add to family members list
        family.getMembers().add(savedMember.getId());
        familyRepository.save(family);

        return UserDTO.fromEntity(savedMember);
    }

    @Transactional
    public void removeFamilyMember(String headId, String memberId) {
        User head = userRepository.findById(headId)
                .orElseThrow(() -> new RuntimeException("Head user not found"));

        if (head.getRole() != User.UserRole.HEAD) {
            throw new RuntimeException("Only head of family can remove members");
        }

        Family family = familyRepository.findById(head.getFamilyId())
                .orElseThrow(() -> new RuntimeException("Family not found"));

        if (memberId.equals(headId)) {
            throw new RuntimeException("Cannot remove head of family");
        }

        family.getMembers().remove(memberId);
        familyRepository.save(family);

        userRepository.deleteById(memberId);
    }

    public FamilyDTO getFamilyById(String familyId) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new RuntimeException("Family not found"));
        return FamilyDTO.fromEntity(family);
    }

    public FamilyDTO getFamilyByHeadId(String headId) {
        Family family = familyRepository.findByHeadId(headId)
                .orElseThrow(() -> new RuntimeException("Family not found"));
        return FamilyDTO.fromEntity(family);
    }

    public List<UserDTO> getAllFamilyMembers(String familyId) {
        return userRepository.findByFamilyId(familyId).stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
}

