package ro.isf.aspects;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS, reason = "Too many requests. Requests are limited by time")
public class TimeRateLimitException extends RuntimeException{
    public TimeRateLimitException(long millis){
        super("Too many attempts in " + millis + " ms");
    }
}
