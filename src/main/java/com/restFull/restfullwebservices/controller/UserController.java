package com.restFull.restfullwebservices.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.restFull.restfullwebservices.beans.User;
import com.restFull.restfullwebservices.dao.UserDaoService;

import java.net.URI;
import java.util.List;
import org.springframework.hateoas.Resource;
import com.restFull.restfullwebservices.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();

    }

    /**
     * Make HATEAOS = hyper media as Engine of App State
     *
     */
    @GetMapping("/user/{id}")
    public Resource<User> findOne(@PathVariable int id){
       User user = service.findOne(id);

       if (user == null){
           throw new UserNotFoundException("id - " + id + " are not found");
       }

       Resource<User> resource = new Resource<>(user);
        ControllerLinkBuilder link =
        linkTo(methodOn( this.getClass()).findOne(user.getUserId()));
        ControllerLinkBuilder link1 =
                linkTo(methodOn( this.getClass()).retrieveAllUsers());
        resource.add(link.withRel("get-user"));
        resource.add(link1.withRel("get-All-user"));

        return resource;
    }

    @PostMapping("/user")
    public ResponseEntity saveUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getUserId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/user/{id}")
    public void deleteById(@PathVariable int id){
        User user = service.deleteById(id);
        if (user == null)
            throw new UserNotFoundException("id - " + id + " are not found");
    }
}
