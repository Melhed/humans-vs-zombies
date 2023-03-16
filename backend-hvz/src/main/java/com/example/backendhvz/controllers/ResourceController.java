package com.example.backendhvz.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/resources")
public class ResourceController {

    @GetMapping("public")
    public ResponseEntity getPublic() {
        /*ResponseMessage message = new ResponseMessage();
        message.setMessage("Public resources");*/
        return ResponseEntity.ok("public");
    }

    @GetMapping("authenticated")
    public ResponseEntity getAuthenticated() {
        return ResponseEntity.ok("authenticated");
    }

    @GetMapping("authorized")
    public ResponseEntity getAuthorized() {
        return ResponseEntity.ok("authorized");
    }

    @GetMapping("authorized/offline")
    public ResponseEntity getAuthorizedOffline() {
        return ResponseEntity.ok("authorized offline");
    }
}