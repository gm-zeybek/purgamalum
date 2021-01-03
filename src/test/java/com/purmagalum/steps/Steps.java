package com.purmagalum.steps;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.purmagalum.utils.utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Steps {
    
    
    static {
        RestAssured.baseURI = "https://www.purgomalum.com/service/";
    }
    
   private Response response;
   private String text;
   private String contentType;
   private String service;
    
    
    
    @Given("^User enters text (.*) and contentType (.*)$")
    public void user_enters_text_and_content_type(String text, String contentType) {
        
        // makes local variables global
        this.text = text;
        this.contentType = contentType;
        
        // calls service method and builds response instance
        response = utils.getService(text, contentType);
    }
    
  
    
    @Then("^should see that result matches the given content Type$")
    public void should_see_that_result_is_matches_the_given_content_type() {
    
        // uses response instance and verifies given entry
        utils.verify_(text, contentType, response,text );
    }
    
    
    
    @Given("^User enters text (.*) and checks (.*)$")
    public void user_enters_text_and_checks_(String text, String service) {
    
        // makes local variables global
        this.text = text;
        this.service = service;
    
        // calls service method and builds response instance
        this.response = utils.getService(text, service);
    
        
    }
    
    
    @Then("^be able to verify that either contains or not profanity word and yield to (.*)$")
    public void be_able_to_verify_that_either_contains_or_not_profanity_word_and_yield_to_(String result) {
    
        // verifies profanity
        utils.verifyProfanity(text, service, response, result);
    }
    
    
    @Given("^User enters text (.*) with the format (.*)$")
    public void user_enters_text_with_the_format(String originalText, String contentType) {
    
        // makes local variables global
        this.text = originalText;
        this.contentType = contentType;
    
    }
    
    @When("^add the advance service (.*) with (.*) and (.*) with (.*)$")
    public void add_the_advance_service_with_and_with(String service1, String select, String service2, String replace) {
    
        // uses overloaded service method for adding advance query parameters
        this.response =utils.getService(text,contentType,service1,select,service2,replace);
        
    }
    
    @Then("^be able to verify that result (.*) will be as expected$")
    public void be_able_to_verify_that_will_be_as_expected(String result) {
        
        // verifies the entities
        utils.verify_(text, contentType, response,result );
    }
    
    @Then("^be able to verify that error (.*) will be as expected$")
    public void be_able_to_verify_that_error_will_be_as_expected(String error) {
        
        // verifies the error statement
        utils.verifyError_(text, contentType, response,error);
    }
    
}
