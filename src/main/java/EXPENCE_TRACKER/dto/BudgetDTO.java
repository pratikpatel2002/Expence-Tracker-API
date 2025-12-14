package EXPENCE_TRACKER.dto;

import EXPENCE_TRACKER.entity.Budget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {
    private String id;
    private String familyId;
    private Integer year;
    private Integer month;
    private BigDecimal amount;
    private Map<String, BigDecimal> memberBudgets;

    public static BudgetDTO fromEntity(Budget budget) {
        BudgetDTO dto = new BudgetDTO();
        dto.setId(budget.getId());
        dto.setFamilyId(budget.getFamilyId());
        dto.setYear(budget.getYear());
        dto.setMonth(budget.getMonth());
        dto.setAmount(budget.getAmount());
        dto.setMemberBudgets(budget.getMemberBudgets());
        return dto;
    }
}





