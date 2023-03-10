package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.HvZUserDTO;
import com.example.backendhvz.mappers.HvZUserMapper;
import com.example.backendhvz.models.HvZUser;
import com.example.backendhvz.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("api/v1/user")
public class HvZUserController {

    private final UserService userService;
    private final HvZUserMapper hvZUserMapper;

    public HvZUserController(UserService userService, HvZUserMapper hvZUserMapper) {
        this.userService = userService;
        this.hvZUserMapper = hvZUserMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<HvZUserDTO>> findAll() {
        return ResponseEntity.ok(hvZUserMapper.hvZUsersToHvZUserDtos(userService.findAll()));
    }

    @GetMapping("{userId}")
    public ResponseEntity<HvZUserDTO> findById(@PathVariable Long userId) {
        return ResponseEntity.ok(hvZUserMapper.hvZUserToHvZUserDto(userService.findById(userId)));
    }

    @PostMapping
    public ResponseEntity<HvZUserDTO> add(@RequestBody HvZUserDTO userDTO) {
        HvZUser user = hvZUserMapper.hvZUserDTOToHvZUser(userDTO);
        userService.add(user);
        URI location = URI.create("/" + user.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{userId}")
    public ResponseEntity<HvZUserDTO> update(@PathVariable Long userId, @RequestBody HvZUserDTO userDTO) {
        if(userId != userDTO.getId()) return null;
        return ResponseEntity.ok(hvZUserMapper.hvZUserToHvZUserDto(userService.update(hvZUserMapper.hvZUserDTOToHvZUser(userDTO))));

    }

    @DeleteMapping("{userId}")
    public ResponseEntity deleteById(@PathVariable Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
