package ee.coop.laenbe.repository;

import ee.coop.laenbe.entity.LoanApplication;
import ee.coop.laenbe.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    Optional<LoanApplication> findById(Long id);

    boolean existsByPersonalCodeAndStatusIn(String personalCode, List<LoanStatus> statuses);

    Optional<LoanApplication> findByIdAndStatus(Long id, LoanStatus status);
}
