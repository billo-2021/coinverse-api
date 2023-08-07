package com.coinverse.api.common.config.routes;

public class AdministrationRoutes {
    public static final String PATH = BaseRoute.PATH + "/administration";
    public static final String USERS = "/users";
    public static final String DISABLE_USER_ACCOUNT = "/users/{username}/disable-account";
    public static final String ENABLE_USER_ACCOUNT = "/users/{username}/enable-account";
    public static final String CRYPTO = "/crypto";
    public static final String UPDATE_CRYPTO_CURRENCY = "/crypto/{currencyCode}";
}
