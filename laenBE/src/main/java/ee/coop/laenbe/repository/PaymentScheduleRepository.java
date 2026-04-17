package ee.coop.laenbe.repository;

import ee.coop.laenbe.entity.PaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentScheduleRepository extends JpaRepository<PaymentSchedule, Long> {

    Optional<PaymentSchedule> findById(Long paymentScheduleId);
}
