package com.example.gestionchampionnatapi.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "le champ fistName ne peut pas être vide")
    @NotNull(message =  "le champ firstName ne peut pas être null")
    private String firstName;

    @NotBlank(message = "le champ lastName ne peut pas être vide")
    @NotNull(message =  "le champ lastName ne peut pas être null")
    private String lastName;
    @NotBlank(message = "le champ email ne peut pas être vide")
    @NotNull(message =  "le champ email ne peut pas être null")
    private String email;
    @NotBlank(message = "le champ password ne peut pas être vide")
    @NotNull(message =  "le champ password ne peut pas être null")
    private  String password;

    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat()
    private LocalDate creationDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @PrePersist
    protected void onCreate() {
        creationDate = LocalDate.now();
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.creationDate  = LocalDate.now();
    }

    public User(){

    }
}
