package com.crowdstore.restapi.auth;

import com.crowdstore.models.auth.Credentials;
import com.crowdstore.models.users.AuthenticatedUser;
import com.crowdstore.restapi.common.AbstractRestService;
import com.crowdstore.restapi.common.RestSession;
import com.jayway.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author fcamblor
 */
@Component
public class AuthenticationRestService extends AbstractRestService {

    public RestSession authenticate(String email, String hashedPassword) {
        return authenticate(new Credentials(email, hashedPassword));
    }

    /**
     * Open a new serverside session with given login / password then return the
     * generated jsessionid that you will have to pass to the server on your next calls
     */
    public RestSession authenticate(Credentials credentials){

        Response response = postWithExpectedStatusCode(null, "auth/authenticate", credentials, HttpStatus.OK.value());
        String jsessionId = response.sessionId();
        AuthenticatedUser authenticatedUser = convertResponseContentAs(AuthenticatedUser.class, response, true);

        return new RestSession(jsessionId, authenticatedUser);
    }
}
