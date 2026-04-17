package ee.coop.laenbe.entity;

import ee.coop.laenbe.enums.LoanStatus;
import ee.coop.laenbe.enums.RejectionReason;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "loan_application")
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name", length = 32, nullable = false)
    private String firstName;

    @Column(name="last_name", length = 32, nullable = false)
    private String lastName;

    @Column(name="personal_code", length = 11, nullable = false)
    private String personalCode;

    @Column(name="loan_period_months", nullable = false)
    private Integer loanPeriodMonths;

    @Column(name="interest_margin", nullable = false, precision = 10, scale = 3)
    private BigDecimal interestMargin;

    @Column(name="base_interest_rate", nullable = false, precision = 10, scale = 3)
    private BigDecimal baseInterestRate;

    @Column(name="loan_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal loanAmount;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private LoanStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name="rejection_reason")
    private RejectionReason rejectionReason;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public LoanApplication() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public Integer getLoanPeriodMonths() {
        return loanPeriodMonths;
    }

    public void setLoanPeriodMonths(Integer loanPeriodMonths) {
        this.loanPeriodMonths = loanPeriodMonths;
    }

    public BigDecimal getInterestMargin() {
        return interestMargin;
    }

    public void setInterestMargin(BigDecimal interestMargin) {
        this.interestMargin = interestMargin;
    }

    public BigDecimal getBaseInterestRate() {
        return baseInterestRate;
    }

    public void setBaseInterestRate(BigDecimal baseInterestRate) {
        this.baseInterestRate = baseInterestRate;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public RejectionReason getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(RejectionReason rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

