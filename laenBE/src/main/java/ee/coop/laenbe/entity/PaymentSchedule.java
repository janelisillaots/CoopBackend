package ee.coop.laenbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name="payment_schedule")
public class PaymentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="loan_application_id", nullable = false)
    private LoanApplication loanApplication;

    @Setter
    @Column(nullable = false)
    private Integer paymentNumber;

    @Setter
    @Column(name="payment_date", nullable = false)
    private LocalDate paymentDate;

    @Setter
    @Column(name="principal", nullable = false, precision = 15, scale = 2)
    private BigDecimal principal;

    @Setter
    @Column(name="interest", nullable = false, precision = 15, scale = 2)
    private BigDecimal interest;

    @Setter
    @Column(name="total_payment", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalPayment;

    @Setter
    @Column(name="remaining_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal remainingBalance;

    public PaymentSchedule() {
    }

}
