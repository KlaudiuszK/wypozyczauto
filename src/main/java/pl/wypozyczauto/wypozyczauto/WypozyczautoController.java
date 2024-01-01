package pl.wypozyczauto.wypozyczauto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auta")
public class WypozyczautoController {
    @Autowired
    private CarService carService;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/addCar")
    @SendTo("/topic/cars")
    public List<Car> addCar(Car car) {
        carService.addCar(car);
        return carService.getAllCars();
    }

    @MessageMapping("/addRentalAdmin")
    @SendTo("/topic/rentals")
    public List<Rental> addRentalAdmin(String rental) {
        System.out.println("dodana rezerwacja "+ rental);
        rentalService.addRentalAdmin(rental);
        return rentalService.getAllRentals();
    }

    @MessageMapping("/reserveCar")
    @SendTo("/topic/cars")
    public Car reserveCar (ReservationRequest reservationRequest){
        System.out.println("Received reserveCar message for car ID: " + reservationRequest.getCarId());
        Long carId = reservationRequest.getCarId();
        Optional<Car> optionalCar = carService.getCar(carId);

        if (optionalCar.isPresent()) {
            System.out.println("Car found, processing reservation...");
            Car car = optionalCar.get();

            // Tworzymy nowy obiekt Rental na podstawie informacji o rezerwacji
            System.out.println("getRentalDate: " + reservationRequest.getReservation().getRentalDate());
            System.out.println("getRentalDate2: " + reservationRequest.getReservation().getRentalDate());
            Rental rental = new Rental();
            rental.setRentalDate(reservationRequest.getReservation().getRentalDate());
            rental.setReturnDate(reservationRequest.getReservation().getReturnDate());
            rental.setTempCarId(carId);
            rental.setCar(car);

            // Zapisujemy Rental do bazy wypożyczeń
            rentalService.addRental(rental);

            //carService.removeCar(carId);

            return car; // Zwracamy zaktualizowany obiekt Car do klientów WebSocket
        }

        return null; // Obsługa błędu, gdy samochód nie został znaleziony
    }

    @MessageMapping("/removeCar")
    @SendTo("/topic/cars")
    public List<Car> removeCar(Long carId){
        carService.removeCar(carId);
        return carService.getAllCars();
    }

    @MessageMapping("/removeRental")
    @SendTo("/topic/rentals")
    public List<Rental> removeRental(Long rentalId){
        try {
            rentalService.removeRental(rentalId);
            return rentalService.getAllRentals();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @MessageMapping("/getCars")
    @SendTo("/topic/cars")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @MessageMapping("/getRentals")
    @SendTo("/topic/rentals")
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @MessageMapping("/getCar")
    @SendTo("/topics/cars")
    public Optional<Car> getCar(Long carId) { return carService.getCar(carId); }

    @MessageMapping("/getRental")
    @SendTo("/topics/rentals")
    public Optional<Rental> getRental(Long rentalId) { return rentalService.getRental(rentalId); }


    @MessageMapping("/editCar")
    @SendTo("/topic/cars")
    public List<Car> editCar(Car updateCar) {
        try {
            System.out.println("Received editCar message: " + updateCar);

            carService.editCar(updateCar);
            return carService.getAllCars();
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();

        }
    }

    @MessageMapping("/editRental")
    @SendTo("/topic/rentals")
    public List<Rental> editRental(Rental updateRental) {
        try {
            System.out.println("Received editRental message: " + updateRental);

            rentalService.editRental(updateRental);
            return rentalService.getAllRentals();
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();

        }
    }


}
