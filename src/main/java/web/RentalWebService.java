package com.example.car_rental.web;

import com.example.car_rental.entity.Dates;
import com.example.car_rental.entity.Car;
import com.example.car_rental.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RentalWebService {
    
    private final RentalService rentalService;
    
    public RentalWebService(RentalService rentalService) {
        this.rentalService = rentalService;
    }
    
    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return rentalService.getAllCars();
    }

    @GetMapping("/available-cars")
    public List<Car> getAvailableCars() {
        return rentalService.getAvailableCars();
    }

    @GetMapping("/cars/{plateNumber}")
    public Car getCar(@PathVariable("plateNumber") String plateNumber) throws Exception {
        return rentalService.getCarByPlateNumber(plateNumber);
    }
    
    @PutMapping("/cars/{plateNumber}")
    public void rentCar(
            @PathVariable("plateNumber") String plateNumber,
            @RequestParam("rent") boolean rent,
            @RequestBody(required = false) Dates dates) throws Exception {
        
        rentalService.rentCar(plateNumber, rent, dates);
    }
}
