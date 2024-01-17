package pl.wypozyczauto.wypozyczauto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @InjectMocks
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CarRepository carRepository;

    @Test
    void getAllRentals() {
        // Mockowanie zachowania repozytorium
        List<Rental> rentals = List.of(
                new Rental(1L, "John", "Doe", Date.valueOf("2022-01-01"), Date.valueOf("2022-01-10"), 1L, null),
                new Rental(2L, "Jane", "Doe", Date.valueOf("2022-02-01"), Date.valueOf("2022-02-10"), 2L, null)
        );
        when(rentalRepository.findAll()).thenReturn(rentals);

        // Wywołanie metody z serwisu
        List<Rental> result = rentalService.getAllRentals();

        // Sprawdzenie, czy wynik jest zgodny z oczekiwaniem
        assertEquals(rentals, result);
    }

    @Test
    void getRental() {
        // Przygotowanie danych testowych
        Long rentalId = 1L;
        Rental rental = new Rental(rentalId, "John", "Doe", Date.valueOf("2022-01-01"), Date.valueOf("2022-01-10"), 1L, null);

        // Mockowanie zachowania repozytorium
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));

        // Wywołanie metody z serwisu
        Optional<Rental> result = rentalService.getRental(rentalId);

        // Sprawdzenie, czy wynik jest zgodny z oczekiwaniem
        assertEquals(Optional.of(rental), result);
    }

    @Test
    void addRental() {
        // Przygotowanie danych testowych
        Rental rentalToAdd = new Rental(null, "John", "Doe", Date.valueOf("2022-01-01"), Date.valueOf("2022-01-10"), 1L, null);
        Rental rentalAdded = new Rental(1L, "John", "Doe", Date.valueOf("2022-01-01"), Date.valueOf("2022-01-10"), 1L, null);

        // Mockowanie zachowania repozytorium
        when(rentalRepository.save(rentalToAdd)).thenReturn(rentalAdded);

        // Wywołanie metody z serwisu
        Rental result = rentalService.addRental(rentalToAdd);

        // Sprawdzenie, czy wynik jest zgodny z oczekiwaniem
        assertEquals(rentalAdded, result);
    }

    @Test
    void addRentalAdmin() {
        // Przygotowanie danych testowych
        String jsonRental = "{\"id\":1,\"firstName\":\"John\",\"surname\":\"Doe\",\"rentalDate\":\"2022-01-01\",\"returnDate\":\"2022-01-10\",\"tempCarId\":1}";

        // Mockowanie zachowania repozytorium
        when(carRepository.findById(1L)).thenReturn(Optional.of(new Car())); // Symulacja istniejącego samochodu
        when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation -> {
            Rental savedRental = invocation.getArgument(0);
            savedRental.setId(1L);
            return savedRental;
        });

        // Wywołanie metody z serwisu
        rentalService.addRentalAdmin(jsonRental);

        // Sprawdzenie, czy metoda z repozytorium została poprawnie wywołana
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void removeRental() {
        // Przygotowanie danych testowych
        Long rentalIdToRemove = 1L;
        Rental rentalToRemove = new Rental(rentalIdToRemove, "John", "Doe", Date.valueOf("2022-01-01"), Date.valueOf("2022-01-10"), 1L, null);

        // Mockowanie zachowania repozytorium
        when(rentalRepository.findById(rentalIdToRemove)).thenReturn(Optional.of(rentalToRemove));

        // Wywołanie metody z serwisu
        rentalService.removeRental(rentalIdToRemove);
    }

    @Test
    void editRental() {
        // Przygotowanie danych testowych
        Long rentalIdToUpdate = 1L;
        Rental updatedRental = new Rental(rentalIdToUpdate, "John", "Doe", Date.valueOf("2022-01-01"), Date.valueOf("2022-01-10"), 2L, null);

        // Mockowanie zachowania repozytorium
        when(rentalRepository.findById(rentalIdToUpdate)).thenReturn(Optional.of(new Rental())); // Symulacja istniejącej rezerwacji

        // Wywołanie metody z serwisu
        rentalService.editRental(updatedRental);

        // Sprawdzenie, czy metoda z repozytorium została poprawnie wywołana
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

}