package com.example.foodtruck.model.user;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    private Long id;
    protected String name;
    protected String password;

    public User(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return name + " " + password;
    }
}