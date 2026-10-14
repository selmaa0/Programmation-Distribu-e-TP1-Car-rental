# Car Rental REST Service

A simple RESTful service for managing car rentals built with Java Spring Boot.

## Project Structure
```
src/
└── main/
    └── java/
        ├── RentalApplication.java      # Main application class
        ├── entity/                     # Data models
        │   ├── Car.java                # Car entity (plateNumber, brand, price, rental status)
        │   └── Dates.java              # Date range for rentals
        ├── service/                    # Business logic
        │   └── RentalService.java      # Service layer with car management logic
        └── web/                        # REST controllers
            └── RentalWebService.java   # Handles HTTP requests
```

## Features
- Get list of all cars: `GET /cars`
- Get available (unrented) cars: `GET /available-cars`
- Get car details by plate number: `GET /cars/{plateNumber}`
- Rent a car: `PUT /cars/{plateNumber}?rent=true`
- Return a car: `PUT /cars/{plateNumber}?rent=false`

## Technologies
- Java 21
- Spring Boot 4.1.1
- Gradle

## Getting Started

### Prerequisites
- JDK 21 or later
- Gradle (wrapper included)

### Build
```bash
./gradlew build
```

### Run
```bash
./gradlew bootRun
```
The service will start on http://localhost:8080

## API Endpoints

### Get All Cars
```bash
curl http://localhost:8080/cars
```
Response: Array of all cars (both rented and available)

### Get Available Cars
```bash
curl http://localhost:8080/available-cars
```
Response: Array of cars currently available for rent

### Get Car by Plate
```bash
curl http://localhost:8080/cars/11AA22
```
Response: Single car object

### Rent a Car
```bash
curl -X PUT "http://localhost:8080/cars/11AA22?rent=true" \
     -H "Content-Type: application/json" \
     -d '{"begin":"2026-10-15","end":"2026-10-20"}'
```
Response: 200 OK (no content)

### Return a Car
```bash
curl -X PUT "http://localhost:8080/cars/11AA22?rent=false"
```
Response: 200 OK (no content)

## Testing
Run unit tests:
```bash
./gradlew test
```

## Notes
- Uses in-memory storage (data resets on restart)
- Sample data includes 4 cars: Ferrari, BMW, Mercedes, Audi
- Plate numbers: 11AA22, 33BB44, 55CC66, 77DD88
