package com.example.backendhvz.services.appUser;

import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.models.AppUser;
import com.example.backendhvz.repositories.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AppUserServiceImpl implements AppUserService{

    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser add(String uid) {
        // Prevents internal server error for duplicates
        if(appUserRepository.existsById(Long.valueOf(uid)))
            throw new BadRequestException("AppUserServiceImpl: add, filed");
        // Create new user
        AppUser user = new AppUser();
        user.setUid(uid);
        user.setComplete(false);
        return appUserRepository.save(user);
    }

    @Override
    public AppUser getById(String uid) {
        return appUserRepository.findById(Long.valueOf(uid))
                .orElseThrow(() -> new BadRequestException("AppUserServiceImpl: getById, User Not Found"));
    }

    @Override
    public AppUser findById(String s) {
        return null;
    }

    @Override
    public AppUser findById(Long aLong) {
        return null;
    }

    @Override
    public Collection<AppUser> findAll() {
        return null;
    }

    @Override
    public AppUser add(AppUser entity) {
        return null;
    }

    @Override
    public AppUser update(AppUser entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(AppUser entity) {

    }
}
