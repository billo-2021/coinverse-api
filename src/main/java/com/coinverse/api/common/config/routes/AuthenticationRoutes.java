package com.coinverse.api.common.config.routes;

public class AuthenticationRoutes {
    public static final String PATH = BaseRoute.PATH + "/authentication";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String REQUEST_TOKEN = "/request-token";
    public static final String VERIFY = "/verify";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String REQUEST_PASSWORD_TOKEN_USER = "/reset-password/{passwordToken}";
}
