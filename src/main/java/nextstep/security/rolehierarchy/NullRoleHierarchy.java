package nextstep.security.rolehierarchy;

import nextstep.security.authorization.GrantedAuthority;

import java.util.Collection;

public class NullRoleHierarchy implements RoleHierarchy {

    private final Collection<GrantedAuthority> grantedAuthorities;

    public NullRoleHierarchy(final Collection<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<GrantedAuthority> getReachableGrantedAuthorities(Collection<GrantedAuthority> grantedAuthorities) {
        return this.grantedAuthorities;
    }

}
