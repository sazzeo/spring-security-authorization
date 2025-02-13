package nextstep.security.requestmatcher;

public record RequestMatcherEntry<T>(RequestMatcher requestMatcher, T entry) {
}
