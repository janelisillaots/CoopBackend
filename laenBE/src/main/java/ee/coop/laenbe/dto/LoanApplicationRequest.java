package ee.coop.laenbe.dto;

import ee.coop.laenbe.validation.ValidPersonalCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

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

    public void setBaseInterestRate(BigDecimal interestRate) {
        this.baseInterestRate = interestRate;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }
}
