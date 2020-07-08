package de.thm.foodtruckbe.data.entities.order;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.entities.user.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    @JsonManagedReference
    private Location location;

    @ElementCollection
    @CollectionTable(name = "dish_amount_mapping")
    @MapKeyColumn(name = "dish_id")
    @Column(name = "amount")
    protected Map<Dish, Integer> items;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonManagedReference
    protected Customer customer;

    protected double price;

    @Enumerated(EnumType.STRING)
    protected Status status;

    public Order(Customer customer, Location location, Map<Dish, Integer> items) {
        this.customer = customer;
        this.location = location;
        this.items = items;
        this.price = items.entrySet().stream().collect(Collectors.toList()).stream()
                .map(e -> e.getKey().getAdjustedPrice() * e.getValue()).reduce(0d, (a, b) -> a + b);
        this.status = Status.ACCEPTED;
    }

    /**
     * Adds an item to this order and raises its price accordingly. Only accepts
     * positive integers.
     *
     * @param dish
     * @param amount
     * @return
     */
    public abstract boolean addItem(Dish dish, int amount);

    /**
     * Removes the given item.
     *
     * @param dish
     */
    public abstract boolean removeItem(Dish dish);

    @Override
    public String toString() {
        return "Order:\n -> Price: " + price + "\n -> Status: " + status + "\n -> Items: "
                + this.items.entrySet().stream().collect(Collectors.toList()).stream()
                        .map(e -> e.getKey().getName() + " " + e.getValue()).reduce("", (a, b) -> a + ", " + b);
    }

    public enum Status {
        ACCEPTED, CONFIRMED, NOT_POSSIBLE, STARTED, DONE
    }
}