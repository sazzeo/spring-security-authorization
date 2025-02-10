package nextstep.security.authorization;

public record AuthorizationDecision(boolean isSuccess) {
    public static AuthorizationDecision success() {
        return new AuthorizationDecision(true);
    }

    public static AuthorizationDecision fail() {
        return new AuthorizationDecision(false);
    }
    
}
