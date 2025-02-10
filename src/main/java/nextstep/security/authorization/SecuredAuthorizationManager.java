package nextstep.security.authorization;

import nextstep.security.authentication.Authentication;
import org.aopalliance.intercept.MethodInvocation;

public class SecuredAuthorizationManager implements AuthorizationManager<MethodInvocation> {
    @Override
    public AuthorizationDecision check(final Authentication authentication, final MethodInvocation methodInvocation) {
        var method = methodInvocation.getMethod();
        if (method.isAnnotationPresent(Secured.class)) {
            var securedAnnotation = method.getAnnotation(Secured.class);

            if (authentication == null || !authentication.isAuthenticated()) {
                return AuthorizationDecision.fail();
            }

            if (!authentication.getAuthorities().contains(securedAnnotation.value())) {
                return AuthorizationDecision.fail();
            }
        }

        return AuthorizationDecision.success();
    }
}
