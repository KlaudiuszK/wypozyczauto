package pl.wypozyczauto.wypozyczauto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String surname;
    private Date rentalDate;
    private Date returnDate;
    private Long tempCarId;

    @ManyToOne
    @JoinColumn(name = "car_id")
    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIgnore
    private Car car;

    @Override
    public String toString() {
        return "Rental{id=" + id + ", firstName=" + firstName + ", surname=" + surname + ", rentalDate=" + rentalDate + ", returnDate=" + returnDate + ", tempCarId=" + tempCarId + "}";
    }
}
