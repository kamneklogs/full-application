package com.kamneklogs.fullapp.security.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamneklogs.fullapp.core.dto.Message;
import com.kamneklogs.fullapp.security.dto.JWTDTO;
import com.kamneklogs.fullapp.security.dto.LoginUserDTO;
import com.kamneklogs.fullapp.security.dto.NewUserDTO;
import com.kamneklogs.fullapp.security.entity.Role;
import com.kamneklogs.fullapp.security.entity.User;
import com.kamneklogs.fullapp.security.enums.RoleName;
import com.kamneklogs.fullapp.security.jwt.JWTProvider;
import com.kamneklogs.fullapp.security.service.RoleService;
import com.kamneklogs.fullapp.security.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    private UserService userService;

    private RoleService roleService;

    private JWTProvider jwtProvider;

    @Autowired
    public AuthenticationController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
            UserService userService, RoleService roleService, JWTProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/user")
    public ResponseEntity<?> nuevo(@RequestBody NewUserDTO nuevoUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<Message>(new Message("campos mal puestos o email inválido"),
                    HttpStatus.BAD_REQUEST);
        if (userService.existsByUsername(nuevoUsuario.getUsername()))
            return new ResponseEntity<Message>(new Message("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if (userService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity<Message>(new Message("ese email ya existe"), HttpStatus.BAD_REQUEST);
        User usuario = new User(nuevoUsuario.getName(), nuevoUsuario.getUsername(), nuevoUsuario.getEmail(),
                passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName(RoleName.ROLE_USER).get());
        if (nuevoUsuario.getRoles().contains("admin"))
            roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        userService.save(usuario);
        return new ResponseEntity<Message>(new Message("usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDTO loginUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<Message>(new Message("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUsuario.getUsername(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJWT(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JWTDTO jwtDto = new JWTDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity<JWTDTO>(jwtDto, HttpStatus.OK);
    }

}
