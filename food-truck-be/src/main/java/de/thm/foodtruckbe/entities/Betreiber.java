package de.thm.foodtruckbe.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Betreiber {

    private String name;

    public Betreiber(String name) {
        this.name = name;
    }
}