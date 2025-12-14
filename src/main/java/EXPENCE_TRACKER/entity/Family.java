package EXPENCE_TRACKER.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "families")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Family {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "head_id", nullable = false)
    private String headId;

    @ElementCollection
    @CollectionTable(name = "family_members", joinColumns = @JoinColumn(name = "family_id"))
    @Column(name = "member_id")
    private List<String> members = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (members == null) {
            members = new ArrayList<>();
        }
    }
}

