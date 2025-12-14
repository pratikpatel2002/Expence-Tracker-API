package EXPENCE_TRACKER.repository;

import EXPENCE_TRACKER.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
    List<Expense> findByUserId(String userId);
    List<Expense> findByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate);
    List<Expense> findByCategory(String category);
    List<Expense> findByUserIdAndCategory(String userId, String category);
    
    @Query("SELECT e FROM Expense e WHERE e.userId IN :userIds")
    List<Expense> findByUserIds(@Param("userIds") List<String> userIds);
    
    @Query("SELECT e FROM Expense e WHERE e.userId IN :userIds AND e.date BETWEEN :startDate AND :endDate")
    List<Expense> findByUserIdsAndDateBetween(@Param("userIds") List<String> userIds, 
                                               @Param("startDate") LocalDate startDate, 
                                               @Param("endDate") LocalDate endDate);
}





