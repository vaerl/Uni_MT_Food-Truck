package de.thm.foodtruckbe.data.dto.user;


import de.thm.foodtruckbe.data.dto.DtoLocation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DtoCustomer extends DtoUser {

    public DtoCustomer(String name, String password) {
        super(name, password);
    }

}
