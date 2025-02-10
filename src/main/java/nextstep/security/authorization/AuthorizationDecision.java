package nextstep.security.authorization;

//FIXME: 현재 인증 에러인지 인가에러인지 구분할 방법이 없음
public record AuthorizationDecision(boolean isSuccess) {
    public static AuthorizationDecision success() {
        return new AuthorizationDecision(true);
    }

    public static AuthorizationDecision fail() {
        return new AuthorizationDecision(false);
    }

}
