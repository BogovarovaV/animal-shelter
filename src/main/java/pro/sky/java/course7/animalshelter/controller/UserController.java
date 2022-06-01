package pro.sky.java.course7.animalshelter.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import pro.sky.java.course7.animalshelter.model.CatAdopter;
//import pro.sky.java.course7.animalshelter.model.DogAdopter;
import pro.sky.java.course7.animalshelter.model.User;
import pro.sky.java.course7.animalshelter.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping
    public ResponseEntity<User> editUser(@RequestBody User user) {
        User editedStudent = userService.createUser(user);
        if (editedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editedStudent);

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<User>> getAllUsers() {
        Collection<User> users = userService.getAllUsers();
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }
}
