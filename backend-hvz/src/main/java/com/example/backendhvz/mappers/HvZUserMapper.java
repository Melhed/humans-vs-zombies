package com.example.backendhvz.mappers;

import com.example.backendhvz.dtos.HvZUserDTO;
import com.example.backendhvz.models.HvZUser;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class HvZUserMapper {

    public abstract HvZUserDTO hvZUserToHvZUserDto(HvZUser hvZUser);
    public abstract HvZUser hvZUserDTOToHvZUser(HvZUserDTO hvZUserDTO);
    public abstract Collection<HvZUserDTO> hvZUsersToHvZUserDtos(Collection<HvZUser> hvZUsers);
    public abstract Collection<HvZUser> hvZUserDtosToHvZUsers(Collection<HvZUserDTO> hvZUserDtos);

}
