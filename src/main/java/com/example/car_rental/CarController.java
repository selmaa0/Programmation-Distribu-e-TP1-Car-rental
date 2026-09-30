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
    
    // Get list of all cars
    @GetMapping("/cars")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Car> listOfCars() {
        return cars;
    }

    // Get list of available (unrented) cars
    @GetMapping("/available-cars")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Car> listOfAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (!car.isRented()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    // Get features of a car using its plate number
    @GetMapping("/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Car aCar(@PathVariable("plateNumber") String plateNumber) throws Exception {
        for (Car car : cars) {
            if (car.getPlateNumber().equals(plateNumber)) {
                return car;
            }
        }
        throw new Exception("Car not found with plate number: " + plateNumber);
    }
    
    // Rent or get back a car
    @PutMapping(value = "/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void rentOrGetBack(
            @PathVariable("plateNumber") String plateNumber,
            @RequestParam(value = "rent", required = true) boolean rent,
            @RequestBody(required = false) Dates dates) throws Exception {
        
        Car car = findCar(plateNumber);
        
        if (rent) {
            // Rent the car
            if (car.isRented()) {
                throw new Exception("Car is already rented");
            }
            car.setRented(true);
            if (dates != null) {
                car.setRentalBegin(dates.getBegin());
                car.setRentalEnd(dates.getEnd());
            }
        } else {
            // Get back the car
            if (!car.isRented()) {
                throw new Exception("Car is not rented");
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
        throw new Exception("Car not found with plate number: " + plateNumber);
    }
}
