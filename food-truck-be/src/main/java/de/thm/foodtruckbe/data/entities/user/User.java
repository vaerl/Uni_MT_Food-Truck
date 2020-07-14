package de.thm.foodtruckbe.data.entities.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue
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
        return name + ": " + password;
    }

}