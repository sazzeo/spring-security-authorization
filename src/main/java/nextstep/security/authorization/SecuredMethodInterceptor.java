package nextstep.security.authorization;

import nextstep.security.authentication.Authentication;
import nextstep.security.context.SecurityContextHolder;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

public class SecuredMethodInterceptor implements MethodInterceptor, PointcutAdvisor, AopInfrastructureBean {

    private final Pointcut pointcut;
    private final AuthorizationManager<MethodInvocation> authorizationManager;

    public SecuredMethodInterceptor(final AuthorizationManager<MethodInvocation> securedAuthorizationManager) {
        this.pointcut = new AnnotationMatchingPointcut(null, Secured.class);
        this.authorizationManager = securedAuthorizationManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var authorizationDecision = authorizationManager.check(authentication, invocation);
        if (!authorizationDecision.isSuccess()) {
            throw new ForbiddenException();
        }
        return invocation.proceed();
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this;
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }
}
