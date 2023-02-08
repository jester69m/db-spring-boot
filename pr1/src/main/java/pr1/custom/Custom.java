package pr1.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

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



    public Custom() {}

    public Custom(int price, int weight, int volume, customStatus customStatus) {
        this.price = price;
        this.weight = weight;
        this.volume = volume;
        this.customStatus = customStatus;
    }

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

    public void setCustomStatus(customStatus customStatus) {
        this.customStatus = customStatus;
    }
}
