package com.example.gestionchampionnatapi.Repository;

import com.example.gestionchampionnatapi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findUserByEmailAndPassword(String email, String password);


}
