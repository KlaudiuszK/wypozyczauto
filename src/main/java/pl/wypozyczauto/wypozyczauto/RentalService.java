package pl.wypozyczauto.wypozyczauto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private CarRepository carRepository;

    private static final Logger logger = LoggerFactory.getLogger(RentalService.class);

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRental(Long rentalId) {  System.out.println("id: " + rentalId); return rentalRepository.findById(rentalId);}

    public Rental addRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public void addRentalAdmin(String jsonRental) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Rental rental = objectMapper.readValue(jsonRental, Rental.class);
            Long carId = rental.getTempCarId();
            Car car = carRepository.findById(carId).orElse(null);

            if (car != null) {
                rental.setCar(car);
                rentalRepository.save(rental);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void removeRental(Long rentalId) {
        try {
            Optional<Rental> optionalRental = rentalRepository.findById(rentalId);

            if (optionalRental.isPresent()) {
                Rental rentalToRemove = optionalRental.get();
                Car car = rentalToRemove.getCar();

                if (car != null) {
                    car.getRentals().remove(rentalToRemove);
                    rentalRepository.deleteById(rentalId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editRental(Rental updatedRental) {
        Optional<Rental> optionalRental = rentalRepository.findById(updatedRental.getId());
        System.out.println("Wybrana rezerwacja " +optionalRental);
        if (optionalRental.isPresent()) {
            Rental existingRental = optionalRental.get();
            // Sprawdź, czy przesłano nową wartość i aktualizuj odpowiednie pole
            if (updatedRental.getRentalDate() != null) {
                existingRental.setRentalDate(updatedRental.getRentalDate());
            }

            if (updatedRental.getReturnDate() != null) {
                existingRental.setReturnDate(updatedRental.getReturnDate());
            }

            if (updatedRental.getTempCarId() != null) {
                existingRental.setTempCarId(updatedRental.getTempCarId());
            }

            rentalRepository.save(existingRental);
        }
    }
}
