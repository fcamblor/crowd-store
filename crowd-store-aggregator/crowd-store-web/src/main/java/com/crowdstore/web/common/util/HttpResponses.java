package com.crowdstore.web.common.util;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author fcamblor
 */
public class HttpResponses {

    /**
     * Utility method that will return a standard redirect JSON bean that will contain
     * every informations for an http redirect that will correctly be handled
     * on clientside
     * It is mainly due to limitations on ajax call not handling redirects (which seems acceptable)
     * More info on http://stackoverflow.com/questions/199099/how-to-manage-a-redirect-request-after-a-jquery-ajax-call
     */
    public static void sendJSONRedirect(String url, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Writer w = response.getWriter();
        w.append(String.format("{ \"status\": %s, \"redirectTo\": \"%s\" }", HttpStatus.SEE_OTHER.value(), url));
        w.flush();
    }
}
