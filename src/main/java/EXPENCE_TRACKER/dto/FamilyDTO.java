package EXPENCE_TRACKER.dto;

import EXPENCE_TRACKER.entity.Family;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDTO {
    private String id;
    private String name;
    private String headId;
    private List<String> members;
    private LocalDateTime createdAt;

    public static FamilyDTO fromEntity(Family family) {
        FamilyDTO dto = new FamilyDTO();
        dto.setId(family.getId());
        dto.setName(family.getName());
        dto.setHeadId(family.getHeadId());
        dto.setMembers(family.getMembers());
        dto.setCreatedAt(family.getCreatedAt());
        return dto;
    }
}





