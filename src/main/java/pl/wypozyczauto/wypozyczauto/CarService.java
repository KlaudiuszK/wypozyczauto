package pl.wypozyczauto.wypozyczauto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    
    public Optional<Car> getCar(Long carId) {  System.out.println("id: " + carId); return carRepository.findById(carId);}


    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public void removeCar(Long carId) {
        // Logika usuwania samochodu na podstawie ID
        carRepository.deleteById(carId);
    }

    public void editCar(Car updatedCar) {
        Optional<Car> optionalCar = carRepository.findById(updatedCar.getId());
        System.out.println("optionalcar" +optionalCar);
        if (optionalCar.isPresent()) {
            Car existingCar = optionalCar.get();
            // Sprawdź, czy przesłano nową wartość i aktualizuj odpowiednie pole
            if (updatedCar.getBrand() != null) {
                existingCar.setBrand(updatedCar.getBrand());
            }

            if (updatedCar.getModel() != null) {
                existingCar.setModel(updatedCar.getModel());
            }

            if (updatedCar.getPrice() != null) {
                existingCar.setPrice(updatedCar.getPrice());
            }

            if (updatedCar.getPhoto() != null) {
                existingCar.setPhoto(updatedCar.getPhoto());
            }

            carRepository.save(existingCar);
        }

    }

}
