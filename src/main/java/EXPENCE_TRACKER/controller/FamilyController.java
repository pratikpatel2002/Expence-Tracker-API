package EXPENCE_TRACKER.controller;

import EXPENCE_TRACKER.dto.FamilyDTO;
import EXPENCE_TRACKER.dto.UserDTO;
import EXPENCE_TRACKER.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/families")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class FamilyController {
    private final FamilyService familyService;

    @PostMapping("/create")
    public ResponseEntity<?> createFamily(
            @RequestParam String headId,
            @RequestParam String name) {
        try {
            if (headId == null || headId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Head ID is required");
            }
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Family name is required");
            }
            
            System.out.println("Creating family - headId: " + headId + ", name: " + name);
            FamilyDTO family = familyService.createFamily(headId, name);
            System.out.println("Family created successfully: " + family.getId());
            return ResponseEntity.ok(family);
        } catch (RuntimeException e) {
            System.err.println("Error creating family: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error creating family: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to create family: " + e.getMessage());
        }
    }

    @PostMapping("/{familyId}/members")
    public ResponseEntity<?> addFamilyMember(
            @RequestParam String headId,
            @RequestParam String name,
            @RequestParam String email) {
        try {
            if (headId == null || headId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Head ID is required");
            }
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Name is required");
            }
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            
            UserDTO member = familyService.addFamilyMember(headId, name, email);
            return ResponseEntity.ok(member);
        } catch (RuntimeException e) {
            System.err.println("Error adding family member: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error adding family member: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to add family member: " + e.getMessage());
        }
    }

    @DeleteMapping("/{familyId}/members/{memberId}")
    public ResponseEntity<?> removeFamilyMember(
            @RequestParam String headId,
            @PathVariable String memberId) {
        try {
            if (headId == null || headId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Head ID is required");
            }
            
            familyService.removeFamilyMember(headId, memberId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            System.err.println("Error removing family member: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error removing family member: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to remove family member: " + e.getMessage());
        }
    }

    @GetMapping("/{familyId}")
    public ResponseEntity<FamilyDTO> getFamilyById(@PathVariable String familyId) {
        try {
            FamilyDTO family = familyService.getFamilyById(familyId);
            return ResponseEntity.ok(family);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/head/{headId}")
    public ResponseEntity<FamilyDTO> getFamilyByHeadId(@PathVariable String headId) {
        try {
            FamilyDTO family = familyService.getFamilyByHeadId(headId);
            return ResponseEntity.ok(family);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{familyId}/members")
    public ResponseEntity<List<UserDTO>> getAllFamilyMembers(@PathVariable String familyId) {
        List<UserDTO> members = familyService.getAllFamilyMembers(familyId);
        return ResponseEntity.ok(members);
    }
}

