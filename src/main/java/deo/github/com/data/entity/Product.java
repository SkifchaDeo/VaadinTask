package deo.github.com.data.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Product extends AbstractEntity {
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "product")
    private List<Customer> buyers = new LinkedList<>();

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
