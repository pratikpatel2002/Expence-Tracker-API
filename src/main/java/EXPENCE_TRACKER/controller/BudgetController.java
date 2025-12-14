package EXPENCE_TRACKER.controller;

import EXPENCE_TRACKER.dto.BudgetDTO;
import EXPENCE_TRACKER.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BudgetController {
    private final BudgetService budgetService;

    @GetMapping("/{familyId}/{year}/{month}")
    public ResponseEntity<BudgetDTO> getBudgetForMonth(
            @PathVariable String familyId,
            @PathVariable Integer year,
            @PathVariable Integer month) {
        BudgetDTO budget = budgetService.getBudgetForMonth(familyId, year, month);
        if (budget != null) {
            return ResponseEntity.ok(budget);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{familyId}/{year}/{month}")
    public ResponseEntity<BudgetDTO> setBudgetForMonth(
            @PathVariable String familyId,
            @PathVariable Integer year,
            @PathVariable Integer month,
            @RequestParam BigDecimal amount) {
        try {
            BudgetDTO budget = budgetService.setBudgetForMonth(familyId, year, month, amount);
            return ResponseEntity.ok(budget);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{familyId}/{year}/{month}/member/{userId}")
    public ResponseEntity<BudgetDTO> setMemberBudget(
            @PathVariable String familyId,
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable String userId,
            @RequestParam BigDecimal amount) {
        try {
            BudgetDTO budget = budgetService.setMemberBudget(familyId, year, month, userId, amount);
            return ResponseEntity.ok(budget);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{familyId}/{year}/{month}/member/{userId}")
    public ResponseEntity<BigDecimal> getMemberBudget(
            @PathVariable String familyId,
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable String userId) {
        BigDecimal amount = budgetService.getMemberBudget(familyId, year, month, userId);
        return ResponseEntity.ok(amount);
    }
}





