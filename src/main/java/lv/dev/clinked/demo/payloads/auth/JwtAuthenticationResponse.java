package lv.dev.clinked.demo.payloads.auth;

public class JwtAuthenticationResponse {

    public static final String TOKEN_TYPE = "Bearer";
    private final String accessToken;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return TOKEN_TYPE;
    }

}
