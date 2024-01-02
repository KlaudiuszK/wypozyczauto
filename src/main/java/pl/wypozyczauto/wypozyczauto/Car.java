package pl.wypozyczauto.wypozyczauto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private Integer price;
    private String photo;
    private boolean available = true;

    @OneToMany(mappedBy = "car", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIgnore
    private List <Rental> rentals;

    @Override
    public String toString() {
        return "Car{id=" + id + ", brand=" + brand + ", model=" + model + ", price=" + price + ", photo=" + photo + ", available=" + available + "}";
    }
}
