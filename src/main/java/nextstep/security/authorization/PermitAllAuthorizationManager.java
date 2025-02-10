package nextstep.security.authorization;

import nextstep.security.authentication.Authentication;
import nextstep.security.authentication.AuthenticationException;

public class PermitAllAuthorizationManager implements AuthorizationManager<Object> {
    @Override
    public AuthorizationDecision check(final Authentication authentication, final Object object) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException();
        }
        return AuthorizationDecision.success();
    }
}
