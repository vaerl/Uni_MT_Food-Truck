package de.thm.foodtruckbe.data.entities.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.thm.foodtruckbe.data.entities.DishWrapper;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.entities.user.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dish")
    @JsonBackReference
    protected List<DishWrapper> items;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonManagedReference
    protected Customer customer;

    protected double price;

    @Enumerated(EnumType.STRING)
    protected Status status;

    public Order(Customer customer, Location location, List<DishWrapper> items) {
        this.customer = customer;
        this.location = location;
        this.items = items;
        this.price = items.stream()
                .map(e -> e.getDish().getAdjustedPrice() * e.getAmount()).reduce(0d, Double::sum);
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
    public abstract boolean addItem(DishWrapper dishWrapper);

    /**
     * Removes the given item.
     *
     * @param dish
     */
    public abstract boolean removeItem(DishWrapper dishWrapper);

    public enum Status {
        ACCEPTED, CONFIRMED, NOT_POSSIBLE, STARTED, DONE
    }
}