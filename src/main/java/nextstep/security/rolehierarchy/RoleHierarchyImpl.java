package nextstep.security.rolehierarchy;

import nextstep.security.authorization.GrantedAuthority;
import nextstep.security.grant.SimpleGrantedAuthority;

import java.util.*;

public class RoleHierarchyImpl implements RoleHierarchy {

    public static final List<GrantedAuthority> NO_AUTHORITIES = Collections.emptyList();

    private final String roleHierarchyStringRepresentation;
    private Map<String, Set<GrantedAuthority>> rolesReachableInOneStepMap = null;
    private Map<String, Set<GrantedAuthority>> rolesReachableInOneOrMoreStepsMap = null;

    private RoleHierarchyImpl(String roleHierarchyStringRepresentation) {
        this.roleHierarchyStringRepresentation = roleHierarchyStringRepresentation;
        buildRolesReachableInOneStepMap();
        buildRolesReachableInOneOrMoreStepsMap();
    }

    public static RoleHierarchyImplBuilder with() {
        return new RoleHierarchyImplBuilder();
    }

    public static class RoleHierarchyImplBuilder {

        private GrantedAuthority role;
        private GrantedAuthority implies;

        public RoleHierarchyImplBuilder role(final GrantedAuthority role) {
            this.role = role;
            return this;
        }

        public RoleHierarchyImplBuilder implies(final GrantedAuthority implies) {
            this.implies = implies;
            return this;
        }

        public RoleHierarchyImpl build() {
            return new RoleHierarchyImpl(String.format("%s > %s", role, implies));
        }
    }


    @Override
    public Collection<GrantedAuthority> getReachableGrantedAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return NO_AUTHORITIES;
        }

        Set<GrantedAuthority> reachableRoles = new HashSet<>();
        Set<String> processedNames = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority() == null) {
                reachableRoles.add(authority);
                continue;
            }
            if (!processedNames.add(authority.getAuthority())) {
                continue;
            }
            reachableRoles.add(authority);
            Set<GrantedAuthority> lowerRoles = this.rolesReachableInOneOrMoreStepsMap.get(authority.getAuthority());
            if (lowerRoles == null) {
                continue;
            }
            for (GrantedAuthority role : lowerRoles) {
                if (processedNames.add(role.getAuthority())) {
                    reachableRoles.add(role);
                }
            }
        }
        return new ArrayList<>(reachableRoles);
    }


    private void buildRolesReachableInOneStepMap() {
        this.rolesReachableInOneStepMap = new HashMap<>();
        for (String line : this.roleHierarchyStringRepresentation.split("\n")) {
            String[] roles = line.trim().split("\\s+>\\s+");
            for (int i = 1; i < roles.length; i++) {
                String higherRole = roles[i - 1];
                GrantedAuthority lowerRole = new SimpleGrantedAuthority(roles[i]);
                Set<GrantedAuthority> rolesReachableInOneStepSet;
                if (!this.rolesReachableInOneStepMap.containsKey(higherRole)) {
                    rolesReachableInOneStepSet = new HashSet<>();
                    this.rolesReachableInOneStepMap.put(higherRole, rolesReachableInOneStepSet);
                } else {
                    rolesReachableInOneStepSet = this.rolesReachableInOneStepMap.get(higherRole);
                }
                rolesReachableInOneStepSet.add(lowerRole);
            }
        }
    }

    private void buildRolesReachableInOneOrMoreStepsMap() {
        this.rolesReachableInOneOrMoreStepsMap = new HashMap<>();
        for (String roleName : this.rolesReachableInOneStepMap.keySet()) {
            Set<GrantedAuthority> rolesToVisitSet = new HashSet<>(this.rolesReachableInOneStepMap.get(roleName));
            Set<GrantedAuthority> visitedRolesSet = new HashSet<>();
            while (!rolesToVisitSet.isEmpty()) {
                GrantedAuthority lowerRole = rolesToVisitSet.iterator().next();
                rolesToVisitSet.remove(lowerRole);
                if (!visitedRolesSet.add(lowerRole)
                        || !this.rolesReachableInOneStepMap.containsKey(lowerRole.getAuthority())) {
                    continue;
                } else if (roleName.equals(lowerRole.getAuthority())) {
                    throw new IllegalArgumentException("ROLE 계층 순환참조 에러");
                }
                rolesToVisitSet.addAll(this.rolesReachableInOneStepMap.get(lowerRole.getAuthority()));
            }
            this.rolesReachableInOneOrMoreStepsMap.put(roleName, visitedRolesSet);
        }

    }


}
