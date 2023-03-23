package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.HvZUserDTO;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.exceptions.RestResponseEntityExceptionHandler;
import com.example.backendhvz.mappers.HvZUserMapper;
import com.example.backendhvz.models.HvZUser;
import com.example.backendhvz.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("api/v1/user")
public class HvZUserController {

    private final UserService userService;
    private final HvZUserMapper hvZUserMapper;
    private final RestResponseEntityExceptionHandler exceptionHandler;

    public HvZUserController(UserService userService, HvZUserMapper hvZUserMapper, RestResponseEntityExceptionHandler exceptionHandler) {
        this.userService = userService;
        this.hvZUserMapper = hvZUserMapper;
        this.exceptionHandler = exceptionHandler;
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
                userService.findById(
                        jwt.getClaimAsString("sub")
                )
        );
    }

    @PostMapping("register")
    public ResponseEntity addNewUserFromJwt(@AuthenticationPrincipal Jwt jwt) {
        HvZUser user = userService.add(jwt.getClaimAsString("sub"));
        URI uri = URI.create("api/v1/users/" + user.getUid());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Collection<HvZUserDTO>> findAll() {
        return ResponseEntity.ok(hvZUserMapper.hvZUsersToHvZUserDtos(userService.findAll()));
    }

    @GetMapping("{userId}")
    public ResponseEntity<Object> findById(@PathVariable String userId) {
        try {
            if(userId.equals(null)) throw new BadRequestException("User ID cannot be null.");
            return ResponseEntity.ok(hvZUserMapper.hvZUserToHvZUserDto(userService.findById(userId)));
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @PostMapping
    public ResponseEntity<HvZUserDTO> add(@RequestBody HvZUserDTO userDTO) {
        System.out.println(userDTO);
        HvZUser user = hvZUserMapper.hvZUserDTOToHvZUser(userDTO);
        userService.add(user);
        URI location = URI.create("/" + user.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{userId}")
    public ResponseEntity<HvZUserDTO> update(@PathVariable String userId, @RequestBody HvZUserDTO userDTO) {
        return ResponseEntity.ok(hvZUserMapper.hvZUserToHvZUserDto(userService.update(hvZUserMapper.hvZUserDTOToHvZUser(userDTO))));

    }

    @DeleteMapping("{userId}")
    public ResponseEntity deleteById(@PathVariable String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
