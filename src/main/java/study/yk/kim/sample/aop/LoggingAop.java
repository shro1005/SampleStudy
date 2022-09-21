package study.yk.kim.sample.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAop {

    // baseLogger : Point cut 범위 설정
    @Pointcut("execution(* study.yk.kim.sample..*.*(..))")
    private void baseLogger(){}

    @Before("baseLogger()")
    public void startBaseLog(JoinPoint jp) {
        Class cs = getClass(jp);
        Method method = getMethod(jp);
        log.info("{} || method : {} START ==================", cs.getName(), method.getName());

        Object[] args = jp.getArgs();
        Arrays.stream(args).forEach(arg ->
                log.info("{} || param type = {} / param value = {}", cs.getName(), (arg != null ? arg.getClass().getSimpleName() : null), arg));
    }

    @AfterReturning(value = "baseLogger()", returning = "returnObj")
    public void endBaseLog(JoinPoint jp, Object returnObj) {
        Class cs = getClass(jp);
        Method method = getMethod(jp);
        log.info("{} || method : {} END ===================", cs.getName(), method.getName());

        log.info("{} || return type = {} / return value = {}", cs.getName(), (returnObj != null ? returnObj.getClass().getSimpleName() : null), returnObj);
    }

    private Method getMethod(JoinPoint jp){
        MethodSignature ms = (MethodSignature) jp.getSignature();
        return ms.getMethod();
    }

    private Class getClass(JoinPoint jp){
        Class aClass = jp.getTarget().getClass();
        return aClass;
    }
}
