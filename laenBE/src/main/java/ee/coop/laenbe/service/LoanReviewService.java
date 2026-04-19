package ee.coop.laenbe.service;

import ee.coop.laenbe.dto.PaymentScheduleResponse;
import ee.coop.laenbe.dto.ReviewResponse;
import ee.coop.laenbe.entity.LoanApplication;
import ee.coop.laenbe.entity.PaymentSchedule;
import ee.coop.laenbe.enums.LoanStatus;
import ee.coop.laenbe.enums.RejectionReason;
import ee.coop.laenbe.repository.LoanApplicationRepository;
import ee.coop.laenbe.repository.PaymentScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanReviewService {
    private final LoanApplicationRepository loanRepo;
    private final PaymentScheduleRepository paymentRepo;

    public LoanReviewService(LoanApplicationRepository loanRepo, PaymentScheduleRepository paymentRepo) {
        this.loanRepo = loanRepo;
        this.paymentRepo = paymentRepo;
    }

    public ReviewResponse getReviewData(Long id) {
        LoanApplication loan = loanRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Loan application not found"));

        List<PaymentSchedule> schedule = paymentRepo.findByLoanApplicationIdOrderByPaymentNumber(id);

        ReviewResponse response = new ReviewResponse();
        response.setId(loan.getId());
        response.setFirstName(loan.getFirstName());
        response.setLastName(loan.getLastName());
        response.setPersonalCode(loan.getPersonalCode());
        response.setLoanAmount(loan.getLoanAmount());
        response.setLoanPeriodMonths(loan.getLoanPeriodMonths());
        response.setBaseInterestRate(loan.getBaseInterestRate());
        response.setInterestMargin(loan.getInterestMargin());
        response.setStatus(loan.getStatus());
        response.setRejectionReason(loan.getRejectionReason());
        response.setCreatedAt(loan.getCreatedAt());

        List<PaymentScheduleResponse> scheduleResponses = schedule.stream().map(p -> {
            PaymentScheduleResponse dto = new PaymentScheduleResponse();
            dto.setPaymentNumber(p.getPaymentNumber());
            dto.setPaymentDate(p.getPaymentDate());
            dto.setInterest(p.getInterest());
            dto.setPrincipal(p.getPrincipal());
            dto.setTotalPayment(p.getTotalPayment());
            dto.setRemainingBalance(p.getRemainingBalance());
            return dto;
        }).toList();

        response.setSchedule(scheduleResponses);

        return response;
    }

    @Transactional
    public void approve(Long id) {
        LoanApplication loan = loanRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Loan application not found"));

        if (loan.getStatus() != LoanStatus.IN_REVIEW) {
            throw new IllegalStateException("Loan application must be IN_REVIEW to approve");
        }

        loan.setStatus(LoanStatus.APPROVED);
        loanRepo.save(loan);
    }

    @Transactional
    public void reject(Long id, RejectionReason reason) {
        LoanApplication loan = loanRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Loan application not found"));

        if (loan.getStatus() != LoanStatus.IN_REVIEW) {
            throw new IllegalStateException("Loan application must be IN_REVIEW to reject");
        }

        loan.setStatus(LoanStatus.APPROVED);
        loan.setRejectionReason(reason);
        loanRepo.save(loan);
    }
}
