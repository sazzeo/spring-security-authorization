package nextstep.security.authorization;

import nextstep.security.authentication.Authentication;
import nextstep.security.authentication.AuthenticationException;

public class PermitAllAuthorizationManager<T> implements AuthorizationManager<T> {
    @Override
    public AuthorizationDecision check(final Authentication authentication, final T object) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException();
        }
        return AuthorizationDecision.success();
    }
}
