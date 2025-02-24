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
        var roleHierarchyImpl = new RoleHierarchyImpl();
        roleHierarchyImpl.setHierarchy("ROLE_A > ROLE_B > ROLE_C");
        var roles = roleHierarchyImpl.getReachableGrantedAuthorities(List.of(TestGrantedAuthority.ROLE_A));
        assertThat(roles).containsOnly(
                TestGrantedAuthority.ROLE_A,
                TestGrantedAuthority.ROLE_B);
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

