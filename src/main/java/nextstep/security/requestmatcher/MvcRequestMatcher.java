package nextstep.security.requestmatcher;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MvcRequestMatcher implements RequestMatcher {
    private final Set<HttpMethod> httpMethods;
    private final Set<String> urls;

    public static MvcRequestMatcher of(final Collection<HttpMethod> httpMethods, final Collection<String> urls) {
        return new MvcRequestMatcher(new HashSet<>(httpMethods), new HashSet<>(urls));
    }

    public static MvcRequestMatcher of(final HttpMethod httpMethod, final String url) {
        return new MvcRequestMatcher(Set.of(httpMethod), Set.of(url));
    }


    public MvcRequestMatcher(final Set<HttpMethod> httpMethods, final Set<String> urls) {
        this.httpMethods = httpMethods;
        this.urls = urls;
    }

    @Override
    public boolean matches(final HttpServletRequest request) {
        if (!matchMethod(request.getMethod())) {
            return false;
        }
        return matchUrl(request.getRequestURI());
    }

    private boolean matchMethod(String method) {
        return httpMethods.contains(HttpMethod.valueOf(method));
    }

    private boolean matchUrl(String url) {
        return urls.contains(url);
    }
}
