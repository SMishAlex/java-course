package com.epam.javacource.sid.spring.aop.aspect;

import java.util.logging.Level;
import java.util.logging.Logger;

public aspect ControllerAspect {

    pointcut shouldBeLogged():
            @annotation(com.epam.javacource.sid.spring.aop.annotations.LogMyCall);

    before(): shouldBeLogged() {
        final Logger logger = Logger.getLogger(thisJoinPoint.getTarget().getClass().getName());
        logger.log(Level.INFO, thisJoinPoint.getSignature().getName());
    }

}
