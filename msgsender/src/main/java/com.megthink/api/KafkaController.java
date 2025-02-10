package com.megthink.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import producer.KafkaProducer;

@RestController
public class KafkaController {

   // private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        boolean success = kafkaProducer.sendMessage(message,"1234");
        if (success) {
            return ResponseEntity.ok("Message sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message.");
        }
    }
    
    @PostMapping("/message")
    public ResponseEntity<String> Gatewaymessage(@RequestBody String message){

    	System.out.println("Recived_Message"+message);
    	if(message!=null) {
            return ResponseEntity.ok("Message sent successfully.");
    	}else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message.");
    	}
    }
    
    
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong");
    }
}
