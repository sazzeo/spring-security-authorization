package nextstep.app.domain;

import nextstep.security.authorization.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
