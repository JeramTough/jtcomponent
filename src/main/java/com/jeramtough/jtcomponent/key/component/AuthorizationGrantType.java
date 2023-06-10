package com.jeramtough.jtcomponent.key.component;

/**
 * <pre>
 * Created on 2020/3/19 1:19
 * by @author JeramTough
 * </pre>
 */

import java.io.Serializable;

/**
 * An authorization grant is a credential representing the resource owner's authorization
 * (to access it's protected resources) to the client and used by the client to obtain an access token.
 *
 * <p>
 * The OAuth 2.0 Authorization Framework defines four standard grant types:
 * authorization code, implicit, resource owner password credentials, and client credentials.
 * It also provides an extensibility mechanism for defining additional grant types.
 *
 * @author Joe Grandja
 * @since 5.0
 */
public final class AuthorizationGrantType implements Serializable {

    private static final long serialVersionUID = 2807995937515534446L;

    public static final AuthorizationGrantType AUTHORIZATION_CODE = new AuthorizationGrantType("authorization_code");
    public static final AuthorizationGrantType IMPLICIT = new AuthorizationGrantType("implicit");
    public static final AuthorizationGrantType REFRESH_TOKEN = new AuthorizationGrantType("refresh_token");
    public static final AuthorizationGrantType CLIENT_CREDENTIALS = new AuthorizationGrantType("client_credentials");
    public static final AuthorizationGrantType PASSWORD = new AuthorizationGrantType("password");
    private final String value;

    /**
     * Constructs an {@code AuthorizationGrantType} using the provided value.
     *
     * @param value the value of the authorization grant type
     */
    public AuthorizationGrantType(String value) {
        this.value = value;
    }

    /**
     * Returns the value of the authorization grant type.
     *
     * @return the value of the authorization grant type
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        AuthorizationGrantType that = (AuthorizationGrantType) obj;
        return this.getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}
