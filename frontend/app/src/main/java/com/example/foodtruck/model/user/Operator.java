package com.example.foodtruck.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Operator extends User {

    public Operator(String name, String password) {
        super(name, password);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Operator) {
            return ((Operator) obj).getName().equals(this.getName());
        }
        return super.equals(obj);
    }
}
