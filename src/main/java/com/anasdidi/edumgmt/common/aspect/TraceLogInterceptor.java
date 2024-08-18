/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.aspect;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@Singleton
@InterceptorBean(TraceLog.class)
class TraceLogInterceptor implements MethodInterceptor<Object, Object> {

  private static final Logger logger = LoggerFactory.getLogger(TraceLogInterceptor.class);
  private final TraceContext traceContext;

  TraceLogInterceptor(TraceContext traceContext) {
    this.traceContext = traceContext;
  }

  @Override
  public @Nullable Object intercept(MethodInvocationContext<Object, Object> context) {
    boolean isController = context.hasAnnotation(Controller.class);
    if (isController) {
      traceContext.setTraceId(UUID.randomUUID());
      MDC.put("traceId", traceContext.getTraceId().toString());
    }

    String classMethod =
        "%s.%s".formatted(context.getDeclaringType().getSimpleName(), context.getMethodName());

    String parameters =
        context.getParameterValueMap().size() > 0
            ? String.join(
                ", ",
                context.getParameterValueMap().entrySet().stream()
                    .map(o -> "%s=%s".formatted(o.getKey(), o.getValue()))
                    .toList())
            : "No parameter";
    logger.trace("[{}] REQ: {}", classMethod, parameters);

    Object result = context.proceed();
    logger.trace("[{}] RES: {}", classMethod, Optional.ofNullable(result).orElse("No result"));

    return result;
  }
}
