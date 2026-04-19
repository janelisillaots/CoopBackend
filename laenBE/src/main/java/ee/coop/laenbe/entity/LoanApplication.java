package ee.coop.laenbe.entity;

import ee.coop.laenbe.enums.LoanStatus;
import ee.coop.laenbe.enums.RejectionReason;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "loan_application")
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "first_name", length = 32, nullable = false)
    private String firstName;

    @Setter
    @Column(name = "last_name", length = 32, nullable = false)
    private String lastName;

    @Setter
    @Column(name = "personal_code", length = 11, nullable = false)
    private String personalCode;

    @Setter
    @Column(name = "loan_period_months", nullable = false)
    private Integer loanPeriodMonths;

    @Setter
    @Column(name = "interest_margin", nullable = false, precision = 10, scale = 3)
    private BigDecimal interestMargin;

    @Setter
    @Column(name = "base_interest_rate", nullable = false, precision = 10, scale = 3)
    private BigDecimal baseInterestRate;

    @Setter
    @Column(name = "loan_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal loanAmount;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "rejection_reason")
    private RejectionReason rejectionReason;

    @Setter
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public LoanApplication() {
    }

}