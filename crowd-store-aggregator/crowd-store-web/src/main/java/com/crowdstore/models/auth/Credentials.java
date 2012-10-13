package com.crowdstore.models.auth;

import java.io.Serializable;

/**
 * @author fcamblor
 */
public class Credentials implements Serializable {
    String login;
    String hashedPassword;
    boolean rememberMe;

    public Credentials(){}

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
