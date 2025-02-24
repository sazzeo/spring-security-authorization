package nextstep.security.authorization;

import nextstep.security.authentication.Authentication;
import nextstep.security.rolehierarchy.RoleHierarchy;

import java.util.Collection;

public class AuthoritiesAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext<Collection<GrantedAuthority>>> {

    private final RoleHierarchy roleHierarchy;

    public AuthoritiesAuthorizationManager(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    @Override
    public AuthorizationDecision check(final Authentication authentication, final RequestAuthorizationContext<Collection<GrantedAuthority>> context) {
        var authorities = roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities());

        var matchAuthority = context.getOthers()
                .stream()
                .anyMatch(authorities::contains);
        if (!matchAuthority) {
            return AuthorizationDecision.fail();
        }
        return AuthorizationDecision.success();
    }
}
