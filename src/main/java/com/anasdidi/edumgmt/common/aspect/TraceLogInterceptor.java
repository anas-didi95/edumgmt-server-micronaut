/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.aspect;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@InterceptorBean(TraceLog.class)
class TraceLogInterceptor implements MethodInterceptor<Object, Object> {

  private static final Logger logger = LoggerFactory.getLogger(TraceLogInterceptor.class);

  @Override
  public @Nullable Object intercept(MethodInvocationContext<Object, Object> context) {
    String classMethod =
        "%s.%s".formatted(context.getDeclaringType().getSimpleName(), context.getMethodName());
    String parameters =
        String.join(
            ",",
            context.getParameterValueMap().entrySet().stream()
                .map(o -> "%s=%s".formatted(o.getKey(), o.getValue()))
                .toList());

    logger.trace("[{}] {}", classMethod, parameters);
    Object result = context.proceed();
    logger.trace("[{}] Returning result: {}", classMethod, result);

    return result;
  }
}
