package com.example.gestionchampionnatapi.Controllers;

import com.example.gestionchampionnatapi.Models.Championship;
import com.example.gestionchampionnatapi.Models.Team;
import com.example.gestionchampionnatapi.Models.User;
import com.example.gestionchampionnatapi.Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> GetUsers()
    {
        List<User> Users = new ArrayList<>(userRepository.findAll());
        return new ResponseEntity<>(Users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> GetUserById(@PathVariable( "id") long id)
    {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent())
        {
            User user = optionalUser.get();
            return new ResponseEntity<>(user, HttpStatus.OK ) ;

        }
        throw  new ResponseStatusException(HttpStatus.NOT_FOUND, " utilisateur introuvable");

    }

    @PostMapping("/user/")
    public ResponseEntity<User> createUser(@Valid @RequestBody  User user, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        }
        else {
            User _user = userRepository.save(new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword()));

            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        }
    }

    @GetMapping("/user/email/password")
    public ResponseEntity<User> getUserByEmailAndPassword(@RequestParam String email, @RequestParam String password)
    {

        // Je recherche l'utilisateur  par son email et mot de passe
        User user = userRepository.findUserByEmailAndPassword(email, password);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            // Gérer le cas où aucun utilisateur n'est trouvé
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
        }
    }

    @DeleteMapping("/user/email")
    public ResponseEntity<User> DeleteUserByName(@RequestParam String email , @RequestParam String password)
    {
        User user = userRepository.findUserByEmailAndPassword(email, password);
        if (user != null) {
            userRepository.delete(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
        }
    }
    @PutMapping("/user/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable(name="userId" ,required = false) Long userId, @RequestBody User userUpdate, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        }
        else {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (!optionalUser.isPresent())
            {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
            }
            User existingUser = optionalUser.get();
            if(userUpdate.getEmail() !=null)
            {
                existingUser.setEmail(userUpdate.getEmail());

            }
            if(userUpdate.getFirstName() !=null)
            {
                existingUser.setFirstName(userUpdate.getFirstName());
            }
            if(userUpdate.getLastName() !=null){
                existingUser.setLastName(userUpdate.getLastName());
            }
            if(userUpdate.getPassword() !=null)
            {
                existingUser.setPassword(userUpdate.getPassword());
            }

            User updatedUser = userRepository.save(existingUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
        }

    }
}

