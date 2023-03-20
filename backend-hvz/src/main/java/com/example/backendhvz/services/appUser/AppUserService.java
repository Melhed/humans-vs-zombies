package com.example.backendhvz.services.appUser;

import com.example.backendhvz.models.AppUser;
import com.example.backendhvz.services.CRUDService;

public interface AppUserService extends CRUDService<AppUser, Long> {
    AppUser add(String uid);

    AppUser getById(String uid);

    AppUser findById(String s);
}
