package EXPENCE_TRACKER.dto;

import EXPENCE_TRACKER.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String role; // "head" or "member"
    private String familyId;
    private LocalDateTime createdAt;

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        String role = user.getRole() == User.UserRole.HEAD ? "head" : "member";
        System.out.println("UserDTO.fromEntity - User role enum: " + user.getRole() + ", Converted to: " + role);
        dto.setRole(role);
        dto.setFamilyId(user.getFamilyId());
        dto.setCreatedAt(user.getCreatedAt());
        System.out.println("UserDTO being returned - role field: " + dto.getRole());
        return dto;
    }
}

