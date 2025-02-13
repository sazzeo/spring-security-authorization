package nextstep.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import nextstep.security.authentication.Authentication;

import java.util.Set;

public class AuthoritiesAuthorizationManager implements AuthorizationManager<HttpServletRequest> {

    private final Set<String> authorities;

    private AuthoritiesAuthorizationManager(final Set<String> authorities) {
        this.authorities = authorities;
    }

    public static AuthoritiesAuthorizationManager of(final Set<String> authorities) {
        return new AuthoritiesAuthorizationManager(authorities);
    }

    public static AuthoritiesAuthorizationManager of(final String authorities) {
        return new AuthoritiesAuthorizationManager(Set.of(authorities));
    }

    @Override
    public AuthorizationDecision check(final Authentication authentication, final HttpServletRequest object) {
        var authorities = authentication.getAuthorities();
        var matchAuthority = this.authorities.stream().anyMatch(authorities::contains);
        if (!matchAuthority) {
            return AuthorizationDecision.fail();
        }
        return AuthorizationDecision.success();
    }
}
