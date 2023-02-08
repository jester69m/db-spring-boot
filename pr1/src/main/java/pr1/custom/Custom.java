package pr1.custom;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import pr1.customer.Customer;
import pr1.department.Department;
import pr1.recipient.Recipient;
import pr1.truck.Truck;

/**
 * Замовлення - вартість, вага, об’єм(всі не можуть від’ємними), статус
 * */
@Entity
public class Custom {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @Min(50)
    private int price;
    @Column
    @PositiveOrZero
    private int weight;
    @Column
    @PositiveOrZero
    private int volume;
    @Enumerated(EnumType.STRING)
    private customStatus customStatus;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name="recipient_id")
    private Recipient recipient;

    @ManyToOne
    @JoinColumn(name="truck_id")
    private Truck truck;

    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;

    public Custom() {}

    public Custom(int price, int weight, int volume, pr1.custom.customStatus customStatus,
                  Customer customer, Recipient recipient, Truck truck, Department department) {
        this.price = price;
        this.weight = weight;
        this.volume = volume;
        this.customStatus = customStatus;
        this.customer = customer;
        this.recipient = recipient;
        this.truck = truck;
        this.department = department;
    }

    public Long getId() { return id;}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public customStatus getCustomStatus() {
        return customStatus;
    }

    public void setCustomStatus(customStatus customStatus) { this.customStatus = customStatus; }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Department getDepartment() {
        return department;
    }

    public void setRoute(Department department) {
        this.department = department;
    }
}
