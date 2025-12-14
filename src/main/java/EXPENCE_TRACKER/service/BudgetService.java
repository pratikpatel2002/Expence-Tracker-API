package EXPENCE_TRACKER.service;

import EXPENCE_TRACKER.dto.BudgetDTO;
import EXPENCE_TRACKER.entity.Budget;
import EXPENCE_TRACKER.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetDTO getBudgetForMonth(String familyId, Integer year, Integer month) {
        Optional<Budget> budgetOpt = budgetRepository.findByFamilyIdAndYearAndMonth(familyId, year, month);
        if (budgetOpt.isPresent()) {
            return BudgetDTO.fromEntity(budgetOpt.get());
        }
        return null;
    }

    @Transactional
    public BudgetDTO setBudgetForMonth(String familyId, Integer year, Integer month, BigDecimal amount) {
        Optional<Budget> budgetOpt = budgetRepository.findByFamilyIdAndYearAndMonth(familyId, year, month);
        
        Budget budget;
        if (budgetOpt.isPresent()) {
            budget = budgetOpt.get();
            budget.setAmount(amount);
        } else {
            budget = new Budget();
            budget.setId(UUID.randomUUID().toString());
            budget.setFamilyId(familyId);
            budget.setYear(year);
            budget.setMonth(month);
            budget.setAmount(amount);
            budget.setMemberBudgets(new HashMap<>());
        }

        Budget savedBudget = budgetRepository.save(budget);
        return BudgetDTO.fromEntity(savedBudget);
    }

    @Transactional
    public BudgetDTO setMemberBudget(String familyId, Integer year, Integer month, String userId, BigDecimal amount) {
        Optional<Budget> budgetOpt = budgetRepository.findByFamilyIdAndYearAndMonth(familyId, year, month);
        
        Budget budget;
        if (budgetOpt.isPresent()) {
            budget = budgetOpt.get();
        } else {
            budget = new Budget();
            budget.setId(UUID.randomUUID().toString());
            budget.setFamilyId(familyId);
            budget.setYear(year);
            budget.setMonth(month);
            budget.setAmount(BigDecimal.ZERO);
            budget.setMemberBudgets(new HashMap<>());
        }

        if (budget.getMemberBudgets() == null) {
            budget.setMemberBudgets(new HashMap<>());
        }
        budget.getMemberBudgets().put(userId, amount);

        Budget savedBudget = budgetRepository.save(budget);
        return BudgetDTO.fromEntity(savedBudget);
    }

    public BigDecimal getMemberBudget(String familyId, Integer year, Integer month, String userId) {
        Optional<Budget> budgetOpt = budgetRepository.findByFamilyIdAndYearAndMonth(familyId, year, month);
        if (budgetOpt.isPresent()) {
            Budget budget = budgetOpt.get();
            if (budget.getMemberBudgets() != null && budget.getMemberBudgets().containsKey(userId)) {
                return budget.getMemberBudgets().get(userId);
            }
        }
        return BigDecimal.ZERO;
    }
}





