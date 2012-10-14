package com.crowdstore.restapi.common;


import com.crowdstore.models.users.AuthenticatedUser;

/**
 * @author fcamblor
 */
public class RestSession {
    String jsessionId;
    AuthenticatedUser authenticatedUser;
    
    public RestSession(String jsessionId, AuthenticatedUser authenticatedUser){
        this.jsessionId = jsessionId;
        this.authenticatedUser = authenticatedUser;
    }

    public String getJsessionId(){
        return this.jsessionId;
    }

    public AuthenticatedUser getAuthenticatedUser() {
        return authenticatedUser;
    }
}
