package pl.wypozyczauto.wypozyczauto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
        carService.addCar(car);
        return carService.getAllCars();
    }

    @MessageMapping("/getCars")
    @SendTo("/topic/cars")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }
}
