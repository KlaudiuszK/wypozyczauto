package pl.wypozyczauto.wypozyczauto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
class CarServiceTest {

	@Autowired
	private CarService carService;

	@MockBean
	private CarRepository carRepository;

	@Test
	void getAllCars() {
		// Przygotuj dane testowe
		List<Car> cars = List.of(new Car(1L, "Toyota", "Camry", 30000, "photo1.jpg", true, null),
				new Car(2L, "Honda", "Civic", 25000, "photo2.jpg", true, null));

		// Mockuj zachowanie repozytorium
		when(carRepository.findAll()).thenReturn(cars);

		// Wywołaj metodę z serwisu
		List<Car> result = carService.getAllCars();

		// Sprawdź, czy wynik jest zgodny z oczekiwaniem
		assertEquals(cars, result);
	}

	@Test
	void getCar() {
		// Przygotuj dane testowe
		Long carId = 1L;
		Car car = new Car(carId, "Toyota", "Camry", 30000, "photo1.jpg", true, null);

		// Mockuj zachowanie repozytorium
		when(carRepository.findById(carId)).thenReturn(Optional.of(car));

		// Wywołaj metodę z serwisu
		Optional<Car> result = carService.getCar(carId);

		// Sprawdź, czy wynik jest zgodny z oczekiwaniem
		assertTrue(result.isPresent());
		assertEquals(car, result.get());
	}

	@Test
	void addCar() {
		// Przygotuj dane testowe
		Car carToAdd = new Car(null, "Toyota", "Camry", 30000, "photo1.jpg", true, null);
		Car carAdded = new Car(1L, "Toyota", "Camry", 30000, "photo1.jpg", true, null);

		// Mockuj zachowanie repozytorium
		when(carRepository.save(carToAdd)).thenReturn(carAdded);

		// Wywołaj metodę z serwisu
		Car result = carService.addCar(carToAdd);

		// Sprawdź, czy wynik jest zgodny z oczekiwaniem
		assertEquals(carAdded, result);
	}

	@Test
	void removeCar() {
		// Przygotuj dane testowe
		Long carIdToRemove = 1L;

		// Wywołaj metodę z serwisu
		carService.removeCar(carIdToRemove);

		// Sprawdź, czy metoda z repozytorium została poprawnie wywołana
		verify(carRepository, times(1)).deleteById(carIdToRemove);
	}

	@Test
	void editCar() {
		// Przygotuj dane testowe
		Long carIdToUpdate = 1L;
		Car updatedCar = new Car(carIdToUpdate, "UpdatedBrand", "UpdatedModel", 40000, "updatedPhoto.jpg", true, null);

		// Mockuj zachowanie repozytorium
		when(carRepository.findById(carIdToUpdate)).thenReturn(Optional.of(new Car())); // Symulacja istniejącego rekordu

		// Wywołaj metodę z serwisu
		carService.editCar(updatedCar);

		// Sprawdź, czy metoda z repozytorium została poprawnie wywołana
		verify(carRepository, times(1)).save(any(Car.class));
	}

}