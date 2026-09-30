package com.example.car_rental;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CarController {
    
    private List<Car> cars = new ArrayList<>();
    
    public CarController() {

        cars.add(new Car("11AA22", "Ferrari", 100));
        cars.add(new Car("33BB44", "BMW", 80));
        cars.add(new Car("55CC66", "Mercedes", 120));
        cars.add(new Car("77DD88", "Audi", 90));
    }
    
    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return cars;
    }

    @GetMapping("/available-cars")
    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (!car.isRented()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    @GetMapping("/cars/{plateNumber}")
    public Car getCar(@PathVariable("plateNumber") String plateNumber) throws Exception {
        for (Car car : cars) {
            if (car.getPlateNumber().equals(plateNumber)) {
                return car;
            }
        }
        throw new Exception("Car not found");
    }
    
    @PutMapping("/cars/{plateNumber}")
    public void rentCar(
            @PathVariable("plateNumber") String plateNumber,
            @RequestParam("rent") boolean rent,
            @RequestBody(required = false) Dates dates) throws Exception {
        
        Car car = findCar(plateNumber);
        
        if (rent) {
            if (car.isRented()) {
                throw new Exception("Car already rented");
            }
            car.setRented(true);
            if (dates != null) {
                car.setRentalBegin(dates.getBegin());
                car.setRentalEnd(dates.getEnd());
            }
        } else {
            if (!car.isRented()) {
                throw new Exception("Car not rented");
            }
            car.setRented(false);
            car.setRentalBegin(null);
            car.setRentalEnd(null);
        }
    }
    
    private Car findCar(String plateNumber) throws Exception {
        for (Car car : cars) {
            if (car.getPlateNumber().equals(plateNumber)) {
                return car;
            }
        }
        throw new Exception("Car not found");
    }
}
