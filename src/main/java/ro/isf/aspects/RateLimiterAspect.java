package ro.isf.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.Semaphore;

@Aspect
@Component
public class RateLimiterAspect {
    private Semaphore semaphore = new Semaphore(2);

    private Map<String, Long> userIdLastAccessedTime = Collections.synchronizedMap(new HashMap<>());

    @Around("execution(public * *(.., @RateLimitByTime (*), ..))")
    public Object limitByTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        //TODO do a better way of extracting the parameter
        String userId = args[0].toString();
        long timeLimit = getTimeLimitFromAnnotation(joinPoint);

        long currentTimeMillis = System.currentTimeMillis();
        Long lastTimeMethodWasAccesedByUser = userIdLastAccessedTime.get(userId);
        if (lastTimeMethodWasAccesedByUser == null) {
            lastTimeMethodWasAccesedByUser = 0L;
        }
        if (currentTimeMillis - lastTimeMethodWasAccesedByUser > timeLimit) {
            userIdLastAccessedTime.put(userId, currentTimeMillis);
            clearEntriesThatPassedTheLimit(timeLimit, currentTimeMillis);
            return joinPoint.proceed();
        } else {
            throw new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS, null, null, null);
        }

    }

    private boolean clearEntriesThatPassedTheLimit(long timeLimit, long currentTimeMillis) {
        return userIdLastAccessedTime.entrySet().removeIf(entry -> currentTimeMillis - entry.getValue() > timeLimit);
    }

    private long getTimeLimitFromAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        List<Parameter> parameters = Arrays.asList(method.getParameters());
        long numberOfRateLimitOnParameters = parameters.stream().filter(parameter -> Arrays.asList(parameter.getAnnotationsByType(RateLimitByTime.class)).size() > 0).count();
        if (numberOfRateLimitOnParameters == 1) {
            Optional<Parameter> parameterWithLimit = parameters.stream().filter(parameter -> Arrays.asList(parameter.getAnnotationsByType(RateLimitByTime.class)).size()>0).findFirst();
            return parameterWithLimit.get().getAnnotationsByType(RateLimitByTime.class)[0].ms();
        } else {
            throw new IllegalArgumentException("Too many annotations of type " + RateLimitByTime.class.getName());
        }
    }



    @Around("execution(public * *(.., @RateLimitBySimultanousRequests (*), ..))")
    public void limitBySimultanousRequests(ProceedingJoinPoint joinPoint) throws Throwable {

        semaphore.acquire();
        joinPoint.proceed();
        semaphore.release();
    }
}
