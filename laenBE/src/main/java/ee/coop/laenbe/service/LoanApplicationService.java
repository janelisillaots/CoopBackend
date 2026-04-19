package ee.coop.laenbe.service;

import ee.coop.laenbe.dto.LoanApplicationRequest;
import ee.coop.laenbe.entity.LoanApplication;
import ee.coop.laenbe.entity.PaymentSchedule;
import ee.coop.laenbe.enums.LoanStatus;
import ee.coop.laenbe.enums.RejectionReason;
import ee.coop.laenbe.repository.LoanApplicationRepository;
import ee.coop.laenbe.repository.PaymentScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanRepo;
    private final PaymentScheduleRepository paymentRepo;

    public LoanApplicationService(LoanApplicationRepository loanRepo, PaymentScheduleRepository paymentRepo) {
        this.loanRepo = loanRepo;
        this.paymentRepo = paymentRepo;
    }

    @Value("${loan.max-age}")
    private int maxAge;

    @Transactional
    public LoanApplication submitApplication(LoanApplicationRequest request) {
        boolean applicationExists = loanRepo.existsByPersonalCodeAndStatusIn(request.getPersonalCode(), List.of(LoanStatus.STARTED, LoanStatus.IN_REVIEW));

        if (applicationExists) {
            throw new IllegalStateException("Active application already exists for this person.");
        }

        int age = calculateAge(request.getPersonalCode());

        LoanApplication loan = new LoanApplication();
        loan.setFirstName(request.getFirstName());
        loan.setLastName(request.getLastName());
        loan.setPersonalCode(request.getPersonalCode());
        loan.setLoanAmount(request.getLoanAmount());
        loan.setInterestMargin(request.getInterestMargin());
        loan.setBaseInterestRate(request.getBaseInterestRate());
        loan.setLoanPeriodMonths(request.getLoanPeriodMonths());

        loan.setStatus(LoanStatus.STARTED);
        loan = loanRepo.save(loan);

        if (age > maxAge) {
            loan.setStatus(LoanStatus.REJECTED);
            loan.setRejectionReason(RejectionReason.CUSTOMER_TOO_OLD);
            return loanRepo.save(loan);
        }
        generateSchedule(loan);

        loan.setStatus(LoanStatus.IN_REVIEW);
        loan = loanRepo.save(loan);
        
        return loan;
    }

    private void generateSchedule(LoanApplication loan) {

        BigDecimal loanAmount = loan.getLoanAmount();
        BigDecimal annualRatePercent = loan.getBaseInterestRate().add(loan.getInterestMargin());

        BigDecimal monthlyInterestRate = annualRatePercent.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        int loanPeriodMonths = loan.getLoanPeriodMonths();

        BigDecimal monthlyPayment = calculateAnnuity(loanAmount, monthlyInterestRate, loanPeriodMonths);

        BigDecimal remaining = loanAmount;

        for (int i = 1; i <= loanPeriodMonths; i++) {
            BigDecimal interestPayment = remaining.multiply(monthlyInterestRate);
            BigDecimal principalPayment = monthlyPayment.subtract(interestPayment);
            remaining = remaining.subtract(principalPayment);

            PaymentSchedule paymentSchedule = new PaymentSchedule();
            paymentSchedule.setLoanApplication(loan);
            paymentSchedule.setPaymentNumber(i);
            paymentSchedule.setPaymentDate(java.time.LocalDate.now().plusMonths(i));
            paymentSchedule.setInterest(interestPayment.setScale(2, RoundingMode.HALF_UP));
            paymentSchedule.setPrincipal(principalPayment.setScale(2, RoundingMode.HALF_UP));
            paymentSchedule.setTotalPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP));
            paymentSchedule.setRemainingBalance(remaining.setScale(2, RoundingMode.HALF_UP));

            paymentRepo.save(paymentSchedule);
        }
    }

    private BigDecimal calculateAnnuity(BigDecimal principal, BigDecimal monthlyInterestRate, int loanPeriodMonths) {
        if (monthlyInterestRate.compareTo(BigDecimal.ZERO) == 0) {
           return principal.divide(BigDecimal.valueOf(loanPeriodMonths), 10, RoundingMode.HALF_UP);
        }

        //annuiteedi arvutamine
        BigDecimal oneRPowerN = BigDecimal.ONE.add(monthlyInterestRate).pow(loanPeriodMonths);

        BigDecimal numerator = principal.multiply(monthlyInterestRate).multiply(oneRPowerN);
        BigDecimal denominator = oneRPowerN.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 10, RoundingMode.HALF_UP);
    }

    private int calculateAge(String personalCode) {
        int year = Integer.parseInt(personalCode.substring(1,3));
        year += (personalCode.charAt(0) == '3' || personalCode.charAt(0) == '4') ? 1900 : 2000;
        int month = Integer.parseInt(personalCode.substring(3, 5));
        int  day = Integer.parseInt(personalCode.substring(5, 7));

        return java.time.Period.between(java.time.LocalDate.of(year, month, day), java.time.LocalDate.now()).getYears();
    }
}
