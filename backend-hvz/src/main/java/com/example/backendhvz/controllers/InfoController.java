package com.example.backendhvz.controllers;

import com.example.backendhvz.models.AppUser;
import com.example.backendhvz.services.appUser.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class InfoController {
    private final AppUserService appUserService;

    public InfoController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    @GetMapping("info")
    public ResponseEntity getLoggedInUserInfo(@AuthenticationPrincipal Jwt principal) {
        Map<String, String> map = new HashMap<>();
        map.put("subject", principal.getClaimAsString("sub"));
        map.put("user_name", principal.getClaimAsString("preferred_username"));
        map.put("email", principal.getClaimAsString("email"));
        map.put("first_name", principal.getClaimAsString("given_name"));
        map.put("last_name", principal.getClaimAsString("family_name"));
        map.put("roles", String.valueOf(principal.getClaimAsStringList("roles")));
        return ResponseEntity.ok(map);
    }

    @GetMapping("principal")
    public ResponseEntity getPrincipal(Principal user){
        return ResponseEntity.ok(user);
    }

    @GetMapping("current")
    public ResponseEntity getCurrentlyLoggedInUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(
            appUserService.findById(
                jwt.getClaimAsString("sub")
            )
        );
    }

    @PostMapping("register")
    public ResponseEntity addNewUserFromJwt(@AuthenticationPrincipal Jwt jwt) {
        AppUser user = appUserService.add(jwt.getClaimAsString("sub"));
        URI uri = URI.create("api/v1/users/" + user.getUid());
        return ResponseEntity.created(uri).build();
    }
}
