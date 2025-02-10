package nextstep.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import nextstep.security.authentication.Authentication;
import nextstep.security.authentication.AuthenticationException;

public class RequestAuthorizationManager implements AuthorizationManager<HttpServletRequest> {

    @Override
    public AuthorizationDecision check(final Authentication authentication, final HttpServletRequest request) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException();
        }
        var authorities = authentication.getAuthorities();
        if (!authorities.contains("ADMIN")) {
            return AuthorizationDecision.fail();
        }
        return AuthorizationDecision.success();
    }
}
