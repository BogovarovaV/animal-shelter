package pro.sky.java.course7.animalshelter.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course7.animalshelter.model.Animal;
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
    public ResponseEntity<User> createUser(@RequestBody User user, @RequestParam Animal.AnimalTypes type) {
        if (user != null) {
            User newUser = userService.createUserByVolunteer(user,type);
            return ResponseEntity.ok(newUser);
        } else return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<User> editUser(@RequestBody User user, @RequestParam Animal.AnimalTypes type) {
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        User editedUser = userService.createUserByVolunteer(user, type);
        return ResponseEntity.ok(editedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (userService.getUserById(id) == null) {
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
