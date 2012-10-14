package com.crowdstore.models.auth;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author fcamblor
 */
public class Credentials implements Serializable {
    @NotNull
    @Size(min=4,max=40)
    String login;
    @NotNull
    @Size(min=128,max=128)
    String hashedPassword;
    boolean rememberMe;

    protected Credentials(){}
    public Credentials(String login, String hashedPassword) {
        this.login = login;
        this.hashedPassword = hashedPassword;
    }

    public Credentials setLogin(String _login){
        this.login = _login;
        return this;
    }

    public String getLogin(){
        return this.login;
    }

    public Credentials setHashedPassword(String _hashedPassword){
        this.hashedPassword = _hashedPassword;
        return this;
    }

    public String getHashedPassword(){
        return this.hashedPassword;
    }

    public Credentials setRememberMe(boolean _rememberMe){
        this.rememberMe = _rememberMe;
        return this;
    }

    public boolean getRememberMe(){
        return this.rememberMe;
    }
}
