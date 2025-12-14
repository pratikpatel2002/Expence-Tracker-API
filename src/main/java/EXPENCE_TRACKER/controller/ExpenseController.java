package EXPENCE_TRACKER.controller;

import EXPENCE_TRACKER.dto.ExpenseDTO;
import EXPENCE_TRACKER.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByUserId(@PathVariable String userId) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/family/{familyId}")
    public ResponseEntity<List<ExpenseDTO>> getAllExpensesByFamily(@PathVariable String familyId) {
        List<ExpenseDTO> expenses = expenseService.getAllExpensesByFamily(familyId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByDateRange(
            @PathVariable String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByUserAndDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/category/{category}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByCategory(
            @PathVariable String userId,
            @PathVariable String category) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByCategory(userId, category);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ExpenseDTO> addExpense(
            @PathVariable String userId,
            @RequestBody ExpenseDTO expenseDTO) {
        try {
            ExpenseDTO expense = expenseService.addExpense(userId, expenseDTO);
            return ResponseEntity.ok(expense);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> updateExpense(
            @RequestParam String userId,
            @PathVariable String expenseId,
            @RequestBody ExpenseDTO expenseDTO) {
        try {
            ExpenseDTO expense = expenseService.updateExpense(userId, expenseId, expenseDTO);
            return ResponseEntity.ok(expense);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(
            @RequestParam String userId,
            @PathVariable String expenseId) {
        try {
            expenseService.deleteExpense(userId, expenseId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable String expenseId) {
        try {
            ExpenseDTO expense = expenseService.getExpenseById(expenseId);
            return ResponseEntity.ok(expense);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}





