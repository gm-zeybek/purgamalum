package com.purmagalum.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        publish = false,
        features = "src/test/resources/features/",
        glue = "com.purmagalum.steps",
        tags = "@all"
        
        
)



public class cukes {
}
