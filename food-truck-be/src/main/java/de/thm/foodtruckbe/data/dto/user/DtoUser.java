package de.thm.foodtruckbe.data.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DtoUser {

    private Long id;
    protected String name;
    protected String password;

    public DtoUser(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return name + " " + password;
    }
}