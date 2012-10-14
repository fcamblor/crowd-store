package com.crowdstore.restapi.common;

import com.crowdstore.web.common.json.JacksonNonBlockingObjectMapperFactory;
import com.google.common.base.Strings;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

/**
 * @author fcamblor
 */
public abstract class AbstractRestService {
    protected static Logger LOG = LoggerFactory.getLogger(AbstractRestService.class);

    @Autowired
    JacksonNonBlockingObjectMapperFactory objectMapperFactory;

    private ObjectMapper objectMapper = null;

    public AbstractRestService(){
    }

    protected ObjectMapper getObjectMapper(){
        if(objectMapper == null){
            objectMapper = objectMapperFactory.createObjectMapper();
        }
        return objectMapper;
    }
    
    protected <T> T post(RestSession session, String url, Object param, Class<T> returnType){
        return convertResponseContentAs(returnType, postWithExpectedStatusCode(session, url, param, HttpStatus.OK.value()), true);
    }

    protected Response postWithExpectedStatusCode(RestSession session, String url, Object param,
                                                        int expectedStatusCode){
        return buildRequestSpec(session, param, null).expect().statusCode(expectedStatusCode).when().post(url);
    }

    protected <T> T put(RestSession session, String url, Object param, Class<T> returnType){
        return convertResponseContentAs(returnType, putWithExpectedStatusCode(session, url, param, HttpStatus.OK.value()), true);
    }

    protected Response putWithExpectedStatusCode(RestSession session, String url, Object param,
                                                        int expectedStatusCode){
        return buildRequestSpec(session, param, null).expect().statusCode(expectedStatusCode).when().put(url);
    }
    
    protected <T> T get(RestSession session, String url, Class<T> returnType){
        return get(session, url, returnType, null);
    }

    protected <T> T get(RestSession session, String url, Class<T> returnType, Map<String, ? extends Object> queryParams){
        return get(session, url, returnType, queryParams, true);
    }
    
    protected Response getWithExpectedStatusCode(RestSession session, String url, int expectedStatusCode){
        return getWithExpectedStatusCode(session, url, null, expectedStatusCode);
    }

    protected Response getWithExpectedStatusCode(RestSession session, String url, Map<String, ? extends Object> queryParams,
                                                        int expectedStatusCode){
        return buildRequestSpec(session, null, queryParams).expect().statusCode(expectedStatusCode).when().get(url);
    }
    
    protected <T> T get(RestSession session, String url, Class<T> returnType, Map<String, ? extends Object> queryParams, boolean rethrowTypeConversionException){
        Response res = getWithExpectedStatusCode(session, url, queryParams, HttpStatus.OK.value());
        return convertResponseContentAs(returnType, res, rethrowTypeConversionException);
    }
    
    protected <T> T convertResponseContentAs(Class<T> returnType, Response res, boolean rethrowTypeConversionException){
        String json = res.asString();
        if(Strings.isNullOrEmpty(json)){
            LOG.warn("REST response is empty : returning null !");
            return null;
        }

        try {
            T returnedObject = getObjectMapper().readValue(json, returnType);
            return returnedObject;
        }catch(RuntimeException e){
            if(rethrowTypeConversionException){
                LOG.error("Error while deserializing REST response to "+returnType+" (json was : "+json+")", e);
                throw e;
            } else {
                LOG.warn("Error while deserializing REST response to "+returnType+" (json was : "+json+")", e);
                return null;
            }
        }catch (IOException e){
            if(rethrowTypeConversionException){
                LOG.error("Error while deserializing REST response to "+returnType+" (json was : "+json+")", e);
                throw new RuntimeException(e);
            } else {
                LOG.warn("Error while deserializing REST response to "+returnType+" (json was : "+json+")", e);
                return null;
            }
        }
    }
    
    protected RequestSpecification buildRequestSpec(RestSession session, Object body, Map<String, ?> queryParams){
        RequestSpecification rs = given().
                    contentType("application/json");
        if(session != null){
            rs = rs.sessionId(session.getJsessionId());
        }

        if(queryParams != null){
            // Not a good idea to serialize query params using jackson serializers : spring mvc doesn't use jackson deserializers
            // to deserialize query params : it uses its owns params converters,
            // see http://static.springsource.org/spring/docs/current/spring-framework-reference/html/validation.html#format-FormatterRegistrar-SPI
            //Map<String, ?> translatedQueryParams = translateQueryParams(queryParams);
            rs = rs.queryParameters(queryParams);
        }
        if(body != null){
            try {
                String bodyStr = getObjectMapper().writeValueAsString(body);
                rs = rs.body(bodyStr);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return rs;
    }

    private Map<String, String> translateQueryParams(Map<String, ?> queryParams) {
        Map<String, String> translatedQueryParams = new HashMap<String, String>();
        for(Map.Entry<String,?> paramEntry : queryParams.entrySet()){
            try {
                translatedQueryParams.put(paramEntry.getKey(), getObjectMapper().writeValueAsString(paramEntry.getValue()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return translatedQueryParams;
    }
}
