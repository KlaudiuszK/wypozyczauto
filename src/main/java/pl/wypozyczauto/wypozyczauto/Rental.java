package pl.wypozyczauto.wypozyczauto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date rentalDate;
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
