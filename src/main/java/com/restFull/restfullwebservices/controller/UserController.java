package com.restFull.restfullwebservices.controller;


import com.restFull.restfullwebservices.beans.User;
import com.restFull.restfullwebservices.dao.UserDaoService;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

    @Autowired
    private UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/user/{id}")
    public User findOne(@PathVariable int id){
       User user = service.findOne(id);
       if (user == null){
           throw new UserNotFoundException("id - " + id + " are not found");
       }
        return user;
    }

    @PostMapping("/user")
    public ResponseEntity saveUser(@RequestBody User user){
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
