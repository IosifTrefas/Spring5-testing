package ro.isf.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.isf.aspects.TimeRateLimit;

@RestController
public class RateLimitController {

    @GetMapping("/iosif/user")
    public String getUserById(@TimeRateLimit(ms = 1000) @RequestParam Integer userId) {
        return "result";
    }
}
