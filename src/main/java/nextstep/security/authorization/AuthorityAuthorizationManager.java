package nextstep.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import nextstep.security.authentication.Authentication;

import java.util.Set;

public class AuthorityAuthorizationManager implements AuthorizationManager<HttpServletRequest> {

    private final Set<String> authorities;

    private AuthorityAuthorizationManager(final Set<String> authorities) {
        this.authorities = authorities;
    }

    public static AuthorityAuthorizationManager of(final Set<String> authorities) {
        return new AuthorityAuthorizationManager(authorities);
    }

    public static AuthorityAuthorizationManager of(final String authorities) {
        return new AuthorityAuthorizationManager(Set.of(authorities));
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
