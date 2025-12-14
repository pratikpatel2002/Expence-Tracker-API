package EXPENCE_TRACKER.dto;

import EXPENCE_TRACKER.entity.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private String id;
    private String title;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private String description;
    private String userId;
    private String userName;

    public static ExpenseDTO fromEntity(Expense expense) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setTitle(expense.getTitle());
        dto.setAmount(expense.getAmount());
        dto.setCategory(expense.getCategory());
        dto.setDate(expense.getDate());
        dto.setDescription(expense.getDescription());
        dto.setUserId(expense.getUserId());
        dto.setUserName(expense.getUserName());
        return dto;
    }
}





