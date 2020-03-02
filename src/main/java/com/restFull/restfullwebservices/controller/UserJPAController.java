package com.restFull.restfullwebservices.controller;

import com.restFull.restfullwebservices.beans.Post;
import com.restFull.restfullwebservices.beans.PostRepository;
import com.restFull.restfullwebservices.beans.User;
import com.restFull.restfullwebservices.beans.UserRepository;
import com.restFull.restfullwebservices.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJPAController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();

    }

    /**
     * Make HATEAOS = hyper media as Engine of App State
     */
    @GetMapping("/jpa/user/{id}")
    public Resource<User> findOne(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("id - " + id + " are not found");
        }

        Resource<User> resource = new Resource<>(user.get());
        ControllerLinkBuilder link =
                linkTo(methodOn(this.getClass()).findOne(user.get().getUserId()));
        ControllerLinkBuilder link1 =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(link.withRel("get-user"));
        resource.add(link1.withRel("get-All-user"));

        return resource;
    }

    @PostMapping("/jpa/user")
    public ResponseEntity saveUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getUserId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/user/{id}")
    public void deleteById(@PathVariable int id) {
        if (!userRepository.findById(id).isPresent())
            throw new UserNotFoundException("id - " + id + " are not found");

        userRepository.deleteById(id);
    }

    @GetMapping("/jpa/user/{id}/posts")
    public List<Post> retrievedPost(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("id - " + id + " are not found");
        }
        return userOptional.get().getPosts();

    }

    @PostMapping("/jpa/user/{id}/posts")
    public ResponseEntity createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("id - " + id + " are not found");
        }
        User user = userOptional.get();
        post.setUser(user);
        Post savedPost =  postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
