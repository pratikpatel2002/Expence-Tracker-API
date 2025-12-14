package EXPENCE_TRACKER.service;

import EXPENCE_TRACKER.dto.ExpenseDTO;
import EXPENCE_TRACKER.entity.Expense;
import EXPENCE_TRACKER.entity.User;
import EXPENCE_TRACKER.repository.ExpenseRepository;
import EXPENCE_TRACKER.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public List<ExpenseDTO> getExpensesByUserId(String userId) {
        return expenseRepository.findByUserId(userId).stream()
                .map(ExpenseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getAllExpensesByFamily(String familyId) {
        List<String> userIds = userRepository.findByFamilyId(familyId).stream()
                .map(User::getId)
                .collect(Collectors.toList());
        return expenseRepository.findByUserIds(userIds).stream()
                .map(ExpenseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByUserAndDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate).stream()
                .map(ExpenseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByCategory(String userId, String category) {
        return expenseRepository.findByUserIdAndCategory(userId, category).stream()
                .map(ExpenseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExpenseDTO addExpense(String userId, ExpenseDTO expenseDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setId(UUID.randomUUID().toString());
        expense.setTitle(expenseDTO.getTitle());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDate(expenseDTO.getDate());
        expense.setDescription(expenseDTO.getDescription());
        expense.setUserId(userId);
        expense.setUserName(user.getName());

        Expense savedExpense = expenseRepository.save(expense);
        return ExpenseDTO.fromEntity(savedExpense);
    }

    @Transactional
    public ExpenseDTO updateExpense(String userId, String expenseId, ExpenseDTO expenseDTO) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        // Only allow updating expenses that belong to the user
        if (!expense.getUserId().equals(userId)) {
            throw new RuntimeException("You don't have permission to update this expense");
        }

        expense.setTitle(expenseDTO.getTitle());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDate(expenseDTO.getDate());
        expense.setDescription(expenseDTO.getDescription());

        Expense updatedExpense = expenseRepository.save(expense);
        return ExpenseDTO.fromEntity(updatedExpense);
    }

    @Transactional
    public void deleteExpense(String userId, String expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user can delete this expense (owns it or is head of family)
        boolean canDelete = expense.getUserId().equals(userId) || user.getRole() == User.UserRole.HEAD;
        if (!canDelete) {
            throw new RuntimeException("You don't have permission to delete this expense");
        }

        expenseRepository.deleteById(expenseId);
    }

    public ExpenseDTO getExpenseById(String expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return ExpenseDTO.fromEntity(expense);
    }
}





