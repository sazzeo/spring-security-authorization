package nextstep.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import nextstep.security.authentication.Authentication;

import java.util.Collection;
import java.util.Set;

public class AuthoritiesAuthorizationManager implements AuthorizationManager<Collection<String>> {

    private final Collection<GrantedAuthority> authorities;

    private AuthoritiesAuthorizationManager(final Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public static AuthoritiesAuthorizationManager of(final Collection<GrantedAuthority> authorities) {
        return new AuthoritiesAuthorizationManager(authorities);
    }

    public static AuthoritiesAuthorizationManager of(final GrantedAuthority authority) {
        return new AuthoritiesAuthorizationManager(Set.of(authority));
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
