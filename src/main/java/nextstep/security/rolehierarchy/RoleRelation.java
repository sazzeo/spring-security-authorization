package nextstep.security.rolehierarchy;

import nextstep.security.authorization.GrantedAuthority;

public record RoleRelation(GrantedAuthority parentRole, GrantedAuthority childRole) {
}
