package org.sample.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate template;
    
    @Autowired
    SimpUserRegistry simpUserRegistry;
    
    private final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    
    @RequestMapping(value = "/notify/{user}/{message}", method=RequestMethod.GET)
    @ResponseBody
    public void notifyUser(@PathVariable String user, @PathVariable String message) {
        
        logger.debug("About to send [{}] to user {}", message, user);
        
        template.convertAndSendToUser(user, "/exchange/amq.direct/notifications", message);
    }
    
    @RequestMapping(value = "/userssessions", method=RequestMethod.GET)
    @ResponseBody
    public String getUserSessions() {
        
        logger.debug("About to get all users");
        
        String userOutput = "=== Users ====<br>";
        
        for (SimpUser currentUser : simpUserRegistry.getUsers()) {
            userOutput += currentUser.getName() + "<br>";
        }
        
        userOutput += "==============<br>";
        
        userOutput += "== Sessions ==<br>";
        
        for (SimpUser currentUser : simpUserRegistry.getUsers()) {
            for (SimpSession session : currentUser.getSessions()) {
                userOutput += session.getId() + "<br>";
            }
        }
        
        userOutput += "==============<br>";
        
        return userOutput;
    }
}
