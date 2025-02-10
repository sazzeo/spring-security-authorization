package nextstep.security.requestmatcher;

public class RequestMatcherEntry<T> {
    private final RequestMatcher requestMatcher;
    private final T entry;

    public RequestMatcherEntry(final RequestMatcher requestMatcher, final T entry) {
        this.requestMatcher = requestMatcher;
        this.entry = entry;
    }

}
