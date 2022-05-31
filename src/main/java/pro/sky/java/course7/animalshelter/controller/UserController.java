package pro.sky.java.course7.animalshelter.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course7.animalshelter.model.CatAdopter;
import pro.sky.java.course7.animalshelter.model.DogAdopter;
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

    @PostMapping("/dog")
    public ResponseEntity<User> createDogAdopter(@RequestBody DogAdopter dogAdopter) {
        User newDogAdopter = userService.createDogAdopter(dogAdopter);
        return ResponseEntity.ok(newDogAdopter);
    }

    @PostMapping("/cat")
    public ResponseEntity<User> createCatAdopter(@RequestBody CatAdopter catAdopter) {
        User newCatAdopter = userService.createCatAdopter(catAdopter);
        return ResponseEntity.ok(newCatAdopter);
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
