package com.example.backendhvz.services.user;

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
    public HvZUser findById(Long userId) {
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
    public HvZUser update(HvZUser user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void delete(HvZUser user) {
        userRepository.delete(user);
    }
}
