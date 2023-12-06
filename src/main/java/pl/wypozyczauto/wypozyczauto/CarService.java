package pl.wypozyczauto.wypozyczauto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public void removeCar(Long carId) {
        // Logika usuwania samochodu na podstawie ID
        carRepository.deleteById(carId);
    }

}
