package ru.itis.tracker.api.security;

public class SecurityConstants {

    public static final String AUTHENTICATION_PATH = "/auth/token";
    public static final String LOGOUT_PATH = "/revoke/token";
    public final static String BEARER = "Bearer ";
    public final static String USERNAME_PARAMETER = "email";
    public final static String REDIRECT_URL = AUTHENTICATION_PATH;
    public final static String ROLE_PARAMETER = "role";

}
