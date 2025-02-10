package nextstep.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import nextstep.security.authentication.Authentication;

public class RequestAuthorizationManager implements AuthorizationManager<HttpServletRequest> {

    @Override
    public AuthorizationDecision check(final Authentication authentication, final HttpServletRequest request) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return AuthorizationDecision.fail();
        }
        var authorities = authentication.getAuthorities();
        if (!authorities.contains("ADMIN")) {
            return AuthorizationDecision.fail();
        }
        return AuthorizationDecision.success();
    }
}
