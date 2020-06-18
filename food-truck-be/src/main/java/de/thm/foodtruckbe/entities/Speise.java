package de.thm.foodtruckbe.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Speise {

    private String name;
    private double preis;
    private int portionen;

    public Speise(String name, double preis, int portionen) {
        this.name = name;
        this.preis = preis;
        this.portionen = portionen;
    }
}