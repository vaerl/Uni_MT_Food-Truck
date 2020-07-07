package com.example.foodtruck.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private Long id;

    protected String name;
    protected String password;

    public User(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

}