package nextstep.security.authorization;

import nextstep.security.authentication.Authentication;
import nextstep.security.rolehierarchy.NullRoleHierarchy;
import nextstep.security.rolehierarchy.RoleHierarchy;

import java.util.Collection;

public class RoleHierarchyAuthoritiesAuthorizationManager implements AuthorizationManager<Collection<GrantedAuthority>> {

    private final RoleHierarchy roleHierarchy;
    private final AuthorizationManager delegate;

    public RoleHierarchyAuthoritiesAuthorizationManager() {
        roleHierarchy = new NullRoleHierarchy();
        this.delegate = new AuthoritiesAuthorizationManager(roleHierarchy.getReachableGrantedAuthorities())
    }

    public RoleHierarchyAuthoritiesAuthorizationManager(final RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    @Override
    public AuthorizationDecision check(final Authentication authentication, final Collection<GrantedAuthority> grantedAuthorities) {
//게잇마 겡잇마~

        return null;
    }
}
