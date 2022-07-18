/* package com.kamneklogs.fullapp.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.kamneklogs.fullapp.security.entity.Role;
import com.kamneklogs.fullapp.security.enums.RoleName;
import com.kamneklogs.fullapp.security.service.RoleService;

@Component
public class CreateRoles implements CommandLineRunner {  // Only to create roles, this also works from manual inserts in db

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
        Role roleUser = new Role(RoleName.ROLE_USER);

        roleService.save(roleAdmin);
        roleService.save(roleUser);

    }

}
 */