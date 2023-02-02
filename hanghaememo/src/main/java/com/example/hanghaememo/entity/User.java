package com.example.hanghaememo.entity;

import com.example.hanghaememo.dto.RegisterRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String pwd, UserRoleEnum role)
    {
        this.username = username;
        this.pwd = pwd;
        this.role = role;
    }
}
