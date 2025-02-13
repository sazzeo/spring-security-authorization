package nextstep.security.rolehierarchy;

import nextstep.security.authorization.GrantedAuthority;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleHierarchyImpl implements RoleHierarchy {
    private final Map<GrantedAuthority, Set<GrantedAuthority>> grantMap;

    public RoleHierarchyImpl(final Collection<RoleRelation> roleRelations) {
        this.grantMap = roleRelations.stream()
                .collect(Collectors.groupingBy(
                        RoleRelation::parentRole,
                        Collectors.mapping(RoleRelation::childRole, Collectors.toSet())));

        grantMap.forEach((key, values) -> {
            values.forEach(value -> {
                var childrenChildren = grantMap.get(value);
                if (childrenChildren.contains(key)) {
                    throw new RuntimeException();
                }

            });
        });
    }

    @Override
    public Collection<GrantedAuthority> getReachableGrantedAuthorities(Collection<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .map(grantMap::get)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

    }

}
