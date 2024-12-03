package com.election.electionbackend.models.forum;

import com.election.electionbackend.security.SecureHasher;
import jakarta.persistence.*;

@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(){
        this.role = UserRole.USER;
    }

    public Long getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        this.password = hashPassword(password);
    }

    public String hashPassword(String password){
        return SecureHasher.secureHash("un-" + this.getUsername() + ":" + password);
    }

    public UserRole getRole(){
        return this.role;
    }

    public void setRole(UserRole role){
        this.role = role;
    }

    public boolean verifyPassword(String password){
        return this.password.equals(hashPassword(password));
    }
}
