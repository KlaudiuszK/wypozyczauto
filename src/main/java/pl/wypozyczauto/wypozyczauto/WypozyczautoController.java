package pl.wypozyczauto.wypozyczauto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auta")
public class WypozyczautoController {
    @Autowired
    private CarService carService;

    //@GetMapping
    //public List<Car> getAllCars() {return carService.getAllCars();}

    @MessageMapping("/addCar")
    @SendTo("/topic/cars")
    public List<Car> addCar(Car car) {
        System.out.println("dodany samochod "+ car);
        carService.addCar(car);
        return carService.getAllCars();
    }

    @MessageMapping("/removeCar")
    @SendTo("/topic/cars")
    public List<Car> removeCar(Long carId){
        carService.removeCar(carId);
        return carService.getAllCars();
    }

    @MessageMapping("/getCars")
    @SendTo("/topic/cars")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @MessageMapping("/getCar")
    @SendTo("/topics/cars")
    public Optional<Car> getCar(Long carId) { return carService.getCar(carId); }


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


}
