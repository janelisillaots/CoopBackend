package ee.coop.laenbe.dto;

import ee.coop.laenbe.validation.ValidPersonalCode;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class LoanApplicationRequest {

    @Size(max=32)
    @NotBlank
    private String firstName;

    @Size(max=32)
    @NotBlank
    private String lastName;

    @NotBlank
    private String personalCode;

    @NotNull
    @Min(6)
    @Max(360)
    private Integer loanPeriodMonths;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal interestMargin;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal baseInterestRate;

    @NotNull
    @DecimalMin("5000.0")
    private BigDecimal loanAmount;

}
