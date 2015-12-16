package org.sample.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
   
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    
    private static Class<Application> applicationClass = Application.class;

	
	public static void main(String[] args) {
        
        LOGGER.debug("About to start gateway application...");
        
        SpringApplication.run(applicationClass, args);
	}

}
