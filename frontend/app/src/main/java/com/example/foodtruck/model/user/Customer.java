package com.example.foodtruck.model.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Customer extends User {

    public Customer(String name, String password) {
        super(name, password);
    }

}
