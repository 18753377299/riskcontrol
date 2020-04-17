package com.picc.riskctrl.common;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import pdfc.framework.log.AbstractTraceLogAspect;

/**
 * 默认的方法日志拦截器
 * @author zhouxianli
 * 
 */ 
@Aspect
@Component
public class TraceLogAspect extends AbstractTraceLogAspect{  
	
	@Override
    @Pointcut("execution(public * pdfc.platform.robot..*.*(..))")
	public void traceLogPointcut() {
		//本方法的注解表明了拦截的类和方法（用于调试日志TraceLog）
	}
}
