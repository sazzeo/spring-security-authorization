package nextstep.security.rolehierarchy;

import nextstep.security.authorization.GrantedAuthority;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoleHierarchyImplTest {


    @Test
    @DisplayName("successTest")
    void successTest() {
        var roleHierarchyImpl = RoleHierarchyImpl.with()
                .role(TestGrantedAuthority.ROLE_A).implies(TestGrantedAuthority.ROLE_B)
                .build();

        var roles = roleHierarchyImpl.getReachableGrantedAuthorities(List.of(TestGrantedAuthority.ROLE_A))
                .stream().map(GrantedAuthority::getAuthority);
        assertThat(roles).containsOnly(
                TestGrantedAuthority.ROLE_A.name(),
                TestGrantedAuthority.ROLE_B.name());
    }

    @Test
    @DisplayName("failTest")
    void failTest() {

    }

    private enum TestGrantedAuthority implements GrantedAuthority {
        ROLE_A,
        ROLE_B,
        ROLE_C,
        ROLE_D,
        ROLE_E,
        ;

        @Override
        public String getAuthority() {
            return this.name();
        }
    }
}

