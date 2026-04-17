package ee.coop.laenbe.controller;

import ee.coop.laenbe.dto.LoanApplicationRequest;
import ee.coop.laenbe.entity.LoanApplication;
import ee.coop.laenbe.service.LoanApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan-application")
public class LoanApplicationController {

    private final LoanApplicationService loanService;

    public LoanApplicationController(LoanApplicationService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanApplication> submitApplication(
            @Valid @RequestBody LoanApplicationRequest request
            ) {
        LoanApplication result = loanService.submitApplication(request);
        return ResponseEntity.ok(result);
    }
}
