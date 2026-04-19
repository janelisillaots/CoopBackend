package ee.coop.laenbe.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class PaymentScheduleResponse {
    private int paymentNumber;
    private LocalDate paymentDate;

    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal totalPayment;
    private BigDecimal remainingBalance;

}
