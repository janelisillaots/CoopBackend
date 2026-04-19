package ee.coop.laenbe.controller;

import ee.coop.laenbe.dto.ReviewResponse;
import ee.coop.laenbe.enums.RejectionReason;
import ee.coop.laenbe.service.LoanReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-review")
public class LoanReviewController {
    private final LoanReviewService reviewService;

    public LoanReviewController(LoanReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewData(id));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable Long id) {
        reviewService.approve(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long id, @RequestParam RejectionReason reason) {
        reviewService.reject(id, reason);
        return ResponseEntity.ok().build();
    }
}
