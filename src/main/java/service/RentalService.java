package com.example.car_rental.service;

import com.example.car_rental.entity.Car;
import com.example.car_rental.entity.Dates;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RentalService {
    
    private List<Car> cars = new ArrayList<>();
    
    public RentalService() {
        // Initialize with sample data
        cars.add(new Car("11AA22", "Ferrari", 100));
        cars.add(new Car("33BB44", "BMW", 80));
        cars.add(new Car("55CC66", "Mercedes", 120));
        cars.add(new Car("77DD88", "Audi", 90));
    }
    
    public List<Car> getAllCars() {
        return new ArrayList<>(cars); // Return copy to prevent external modification
    }
    
    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (!car.isRented()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }
    
    public Car getCarByPlateNumber(String plateNumber) throws Exception {
        for (Car car : cars) {
            if (car.getPlateNumber().equals(plateNumber)) {
                return car;
            }
        }
        throw new Exception("Car not found");
    }
    
    public void rentCar(String plateNumber, boolean rent, Dates dates) throws Exception {
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
