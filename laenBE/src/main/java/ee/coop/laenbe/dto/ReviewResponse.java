package ee.coop.laenbe.dto;

import ee.coop.laenbe.enums.LoanStatus;
import ee.coop.laenbe.enums.RejectionReason;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ReviewResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String personalCode;
    private BigDecimal loanAmount;
    private int loanPeriodMonths;
    private BigDecimal baseInterestRate;
    private BigDecimal interestMargin;
    private LoanStatus status;
    private RejectionReason rejectionReason;
    private LocalDateTime createdAt;

    private List<PaymentScheduleResponse> schedule;

}
