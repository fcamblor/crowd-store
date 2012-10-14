package com.crowdstore.service.user;

/**
 * @author fcamblor
 */
public class AuthenticationError extends Exception {

    public AuthenticationError(String message){
        super(message);
    }
}
