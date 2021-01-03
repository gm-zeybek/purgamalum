package com.purmagalum.utils;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class utils {
    
    
    /**
     * Encoding given text into available format
     * @param text
     * @return
     */
    public static String getEncodedText(String text){
        String encodedText = "";
        try {
            encodedText = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedText;
    }
    
    /**
     * Building spec
     * @return
     */
    public static RequestSpecification getSpecification(){
        return given().baseUri(RestAssured.baseURI)
                .urlEncodingEnabled(false);
        
    }
    
    /**
     * Using spec, query parameter and endpoint builds Response instance
     * @param text
     * @param serviceType
     * @return
     */
    public static Response getService(String text, String serviceType) {
        return given().spec(getSpecification())
                .queryParameter("text", getEncodedText(text))
                .get(serviceType);
    }
    
    /**
     * Overloaded method for Responce instance builder
     * @param text
     * @param serviceType
     * @param service1
     * @param select
     * @param service2
     * @param replace
     * @return
     */
    public static Response getService(String text, String serviceType,String service1, String select, String service2, String replace) {
        return given().spec(getSpecification())
                .queryParameter("text", getEncodedText(text))
                .queryParam("add",select)
                .queryParam(service2,replace)
                .get(serviceType);
    }
    
    
    /**
     * verify method
     * @param text
     * @param contentType
     * @param response
     * @param result
     */
    public static void verify_(String text, String contentType, Response response, String result){
        
        switch (contentType) {
            case "xml":
                assertThat(utils.xmlExtractor(response))
                        .as("`%s` formatted text wasn't matched for text `%s`", contentType,
                                text).isEqualTo(result);
                break;
            case "json":
                assertThat(utils.jsonExtractor(response))
                        .as("`%s` formatted text wasn't matched for text `%s`", contentType,
                                text).isEqualTo(result);
                break;
            
            case "plain":
                assertThat(utils.plainExtractor(response))
                        .as("`%s` formatted text wasn't matched for text `%s`", contentType,
                                text).isEqualTo(result);
        }
        
    }
    
    public static void verifyProfanity(String text, String service, Response response, String result){
    
        assertThat(utils.plainExtractor(response))
                .as("text  `%s`  doesn't follow the rule `%s`",text, service
                ).isEqualTo(result);
    
    }
    
    /**
     * Verify method for error messages
     * @param text
     * @param contentType
     * @param response
     * @param result
     */
    public static void verifyError_(String text, String contentType, Response response, String result){
        
        switch (contentType) {
            case "xml":
                assertThat(utils.xmlExtractor(response))
                        .as("`%s` formatted text `%s` didn't yield as expected error", contentType,
                                text).isEqualTo(result);
                break;
            case "json":
                assertThat(utils.jsonErrorExtractor(response))
                        .as("`%s` formatted text `%s` didn't yield as expected error", contentType,
                                text).isEqualTo(result);
                break;
            
            case "plain":
                assertThat(utils.plainExtractor(response))
                        .as("`%s` formatted text `%s` didn't yield as expected error", contentType,
                                text).isEqualTo(result);
        }
        
    }
    
    /**
     * XML extractor
     * @param response
     * @return
     */
    public static String xmlExtractor(Response response){
        return response.body().xmlPath().get().children().iterator().next();
    }
    
    /**
     * Json extractor
     * @param response
     * @return
     */
    public static String jsonExtractor(Response response) {
        return response.body().path("result").toString();
    }
    
    /**
     * jsonError extractor
     * @param response
     * @return
     */
    public static String jsonErrorExtractor(Response response){
        return response.body().path("error").toString();
    }
    
    /**
     * Plain text extractor
     * @param response
     * @return
     */
    public static String plainExtractor(Response response){
        return response.body().asString();
    }
    
}
