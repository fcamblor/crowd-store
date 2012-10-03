package com.crowdstore.web.common.result;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author fcamblor
 */
public class RequestResult {
    public static final RequestResult ok = new RequestResult("ok");
    public static final RequestResult failure = new RequestResult("failure");
    public static final RequestResult TRUE = new RequestResult(Boolean.TRUE.toString());
    public static final RequestResult FALSE = new RequestResult(Boolean.FALSE.toString());

    String result;

    @JsonCreator
    public RequestResult(@JsonProperty("result") String result) {
        this.result = result;
    }

    public String getResult() {
        return this.result;
    }

    public static RequestResult valueOf(boolean b) {
        return b ? TRUE : FALSE;
    }
}
