package de.thm.foodtruckbe.data.entities.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.DishWrapper;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.entities.user.Customer;
import de.thm.foodtruckbe.data.repos.DishRepository;
import de.thm.foodtruckbe.exceptions.EntityNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    @JsonManagedReference
    private Location location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    protected List<DishWrapper> items;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonManagedReference
    protected Customer customer;

    protected double price;

    @Enumerated(EnumType.STRING)
    protected Status status;

    public Order(){
        this.items = new ArrayList<>();
    }

    public Order(Customer customer, Location location) {
        this();
        this.customer = customer;
        this.location = location;
        this.price = 0;
        this.status = Status.ACCEPTED;
    }

    public abstract boolean addItem(DishWrapper dishWrapper);

    public boolean addAllItems(List<DishWrapper> dishWrappers){
        for (DishWrapper dishWrapper:dishWrappers) {
            if(!this.addItem(dishWrapper)){
                return false;
            }
        }
        return true;
    }

    public abstract boolean removeItem(DishWrapper dishWrapper);

    public boolean removeAllItems(List<DishWrapper> dishWrappers){
        for (DishWrapper dishWrapper:dishWrappers) {
            if(!this.removeItem(dishWrapper)){
                return false;
            }
        }
        return true;
    }

    public enum Status {
        ACCEPTED, CONFIRMED, NOT_POSSIBLE, STARTED, DONE
    }

    protected static Dish getDish(Long id, DishRepository dishRepository) {
        Optional<Dish> dish = dishRepository.findById(id);
        if (dish.isPresent()) {
            return dish.get();
        } else {
            throw new EntityNotFoundException("Dish", id);
        }
    }
}