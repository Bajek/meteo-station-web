package pl.ubytes.meteo.security;

/**
 * Created by bajek on 2/17/15.
 */
public class TokenChecker {

    private String token;

    public void setToken(String token) {
        this.token = token;
    }


    public boolean checkToken(String token) {
        return token.equals(this.token);
    }
}
