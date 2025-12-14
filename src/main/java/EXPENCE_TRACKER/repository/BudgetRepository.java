package EXPENCE_TRACKER.repository;

import EXPENCE_TRACKER.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, String> {
    Optional<Budget> findByFamilyIdAndYearAndMonth(String familyId, Integer year, Integer month);
}





