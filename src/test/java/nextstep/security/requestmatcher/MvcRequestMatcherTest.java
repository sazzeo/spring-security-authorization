package nextstep.security.requestmatcher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Set;

class MvcRequestMatcherTest {
    @Test
    @DisplayName("요청 메소드와 url 이 일치하면 true를 반환한다.")
    void successTest() {
        var requestMatcher = MvcRequestMatcher.of(Set.of(HttpMethod.GET), Set.of("/matches"));

        var request = new MockHttpServletRequest();
        request.setRequestURI("/matches");
        request.setMethod("GET");

        Assertions.assertThat(requestMatcher.matches(request)).isTrue();
    }

    @Test
    @DisplayName("요청 메소드가 일치하지 않으면 false를 반환한다")
    void methodFailTest() {
        var requestMatcher = MvcRequestMatcher.of(Set.of(HttpMethod.GET), Set.of("/matches"));

        var request = new MockHttpServletRequest();
        request.setRequestURI("/matches");
        request.setMethod("POST");

        Assertions.assertThat(requestMatcher.matches(request)).isFalse();
    }

    @Test
    @DisplayName("요청 url이 일치하지 않으면 false를 반환한다")
    void urlFailTest() {
        var requestMatcher = MvcRequestMatcher.of(HttpMethod.GET, "/matches");

        var request = new MockHttpServletRequest();
        request.setRequestURI("/fail");
        request.setMethod("GET");

        Assertions.assertThat(requestMatcher.matches(request)).isFalse();
    }
}
