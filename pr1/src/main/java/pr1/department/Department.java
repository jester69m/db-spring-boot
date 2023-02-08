package pr1.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Відділення - назва, код
 * */
@Entity
public class Department {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true,nullable = false)
    private String name;

    public Department(){}

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
