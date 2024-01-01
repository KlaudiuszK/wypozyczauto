package pl.wypozyczauto.wypozyczauto;

public class ReservationRequest {
    private Car car;
    private Rental rental;
    private Long carId;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Rental getReservation() {
        return rental;
    }

    public void setReservation(Rental rental) {
        this.rental = rental;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
