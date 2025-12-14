package EXPENCE_TRACKER.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "budgets", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"family_id", "year", "month"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    @Id
    private String id;

    @Column(name = "family_id", nullable = false)
    private String familyId;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month; // 1-12

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount; // Total family budget

    @ElementCollection
    @CollectionTable(name = "member_budgets", joinColumns = @JoinColumn(name = "budget_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "amount")
    private Map<String, BigDecimal> memberBudgets = new HashMap<>();
}

