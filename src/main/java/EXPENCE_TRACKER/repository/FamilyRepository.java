package EXPENCE_TRACKER.repository;

import EXPENCE_TRACKER.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, String> {
    Optional<Family> findByHeadId(String headId);
}





