package pr1.department;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pr1.custom.Custom;

import java.util.List;

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
    @Column(unique = true,nullable = false)
    private int code;

    @OneToMany(cascade = CascadeType.REMOVE)
    List<Custom> customs;

    public Department(){}

    public Department(String name,int code,List<Custom> customs) {
        this.name = name;
        this.code = code;
        this.customs=customs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Custom> getCustoms() {
        return customs;
    }

    public void setCustoms(List<Custom> customs) {
        this.customs = customs;
    }
    public void addCustom(Custom custom){
        this.customs.add(custom);
    }
}
