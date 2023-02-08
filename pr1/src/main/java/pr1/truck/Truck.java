package pr1.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Вантажівка - місткість об’єму, місткість ваги
 * */
@Entity
public class Truck {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @PositiveOrZero
    @Column
    private int maxVolume;
    @PositiveOrZero
    @Column
    private int maxWeight;

    public Truck(){}

    public Truck(int maxVolume, int maxWeight){
        this.maxVolume = maxVolume;
        this.maxWeight = maxWeight;
    }

    public int getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(int maxVolume) {
        this.maxVolume = maxVolume;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }
}
