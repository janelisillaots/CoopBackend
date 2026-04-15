package ee.coop.laenbe;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCOntroller {

    @GetMapping("/api/health")
    public String health() {
        return "OK";
    }
}