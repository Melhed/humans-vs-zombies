package com.example.backendhvz.services.user;

import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.models.HvZUser;
import com.example.backendhvz.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public HvZUser findById(String userId) {
        if(!userRepository.existsById(userId)) throw new NotFoundException("User with ID "+ userId + " not found.");
        return userRepository.findById(userId).get();
    }

    @Override
    public Collection<HvZUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public HvZUser add(HvZUser user) {
        return userRepository.save(user);
    }

    @Override
    public HvZUser add(String uid) {
        // Prevents internal server error for duplicates
        if(userRepository.existsById(uid))
            throw new BadRequestException("AppUserServiceImpl: add, filed");
        // Create new user
        HvZUser user = new HvZUser();
        user.setUid(uid);
        user.setComplete(false);
        return userRepository.save(user);
    }

    @Override
    public HvZUser getById(String uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new BadRequestException("UserServiceImpl: getById, user not found"));
    }

    @Override
    public HvZUser update(HvZUser user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void delete(HvZUser user) {
        userRepository.delete(user);
    }
}
