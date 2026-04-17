package ee.coop.laenbe.repository;

import ee.coop.laenbe.entity.PaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentScheduleRepository extends JpaRepository<PaymentSchedule, Long> {

    List<PaymentSchedule> findByPaymentScheduleId(Long paymentScheduleId);
}
