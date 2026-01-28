package lld05_factory_pattern.simple_factory_vehicle_rental_system;

/**
ASSIGNMENT: Simple (Normal) Factory Pattern — Vehicle Rental System

PROBLEM STATEMENT
You are building a backend module for a Vehicle Rental application.
The system allows users to rent different types of vehicles.

Currently supported vehicles:
- Car
- Bike
- Truck

Each vehicle:
- Has a base rental price per day
- Can calculate rental cost based on number of days
- Can return basic vehicle details

The client code receives a string input like:
"CAR", "BIKE", or "TRUCK"

Based on this input, the system should create the correct vehicle object
and return it to the client.

The client MUST NOT know which concrete class is being created.
All object creation logic should be centralized in one place.

--------------------------------------------------

REQUIREMENTS

1. Create a common interface:
   Vehicle

   Methods:
   - double getPricePerDay()
   - double calculateRent(int days)
   - String getDetails()

2. Create concrete implementations:
   - Car
   - Bike
   - Truck

   Each class should:
   - Have its own price per day
   - Implement rental calculation logic
   - Return vehicle-specific details

3. Create a Normal Factory class:
   VehicleFactory

   Responsibilities:
   - Accept a string or enum representing vehicle type
   - Decide which concrete Vehicle to create
   - Return the Vehicle interface

4. Client Code:
   - Takes vehicle type input
   - Calls VehicleFactory to get a Vehicle
   - Uses only the Vehicle interface methods
   - Must NOT use `new` for concrete classes

--------------------------------------------------

CONSTRAINTS & RULES

- Factory may use if-else or switch (this is allowed here)
- No object creation logic outside the factory
- Client must not cast to concrete classes
- Adding a new vehicle should require:
  - Adding a new class
  - Modifying ONLY the factory (this limitation is intentional)

--------------------------------------------------

DELIVERABLES

- Java code for:
  - Vehicle interface
  - All concrete vehicle classes
  - VehicleFactory
  - Client / main class
- Short explanation answering:
  "What problem does a Normal Factory solve, and where does it start to break?"

--------------------------------------------------

INTENT OF THIS ASSIGNMENT

This assignment is designed to help you:
- Understand centralized object creation
- See why Normal Factory violates Open/Closed Principle
- Feel the pain that Factory Method later solves

Do NOT over-engineer.
Do NOT use inheritance for factories.
Keep it simple, clean, and honest.

 */

interface Vehicle {
    double getPricePerDay();
    double calculateRent(int days);
    String getDetails();
}

class Car implements Vehicle {
    private final double PRICE_PER_DAY = 50.0;

    @Override
    public double getPricePerDay() {
        return PRICE_PER_DAY;
    }

    @Override
    public double calculateRent(int days) {
        return PRICE_PER_DAY * days;
    }

    @Override
    public String getDetails() {
        return "Vehicle Type: Car, Price per Day: $" + PRICE_PER_DAY;
    }

}

class Bike implements Vehicle {
    private final double PRICE_PER_DAY = 20.0;

    @Override
    public double getPricePerDay() {
        return PRICE_PER_DAY;
    }

    @Override
    public double calculateRent(int days) {
        return PRICE_PER_DAY * days;
    }

    @Override
    public String getDetails() {
        return "Vehicle Type: Bike, Price per Day: $" + PRICE_PER_DAY;
    }
}

class Truck implements Vehicle {
    private final double PRICE_PER_DAY = 80.0;

    @Override
    public double getPricePerDay() {
        return PRICE_PER_DAY;
    }

    @Override
    public double calculateRent(int days) {
        return PRICE_PER_DAY * days;
    }

    @Override
    public String getDetails() {
        return "Vehicle Type: Truck, Price per Day: $" + PRICE_PER_DAY;
    }
}

enum VehicleType {
    CAR,
    BIKE,
    TRUCK
}


class VehicleFactory {
    public static Vehicle createVehicle(VehicleType type) {
        switch (type) {
            case CAR:
                return new Car();
            case BIKE:
                return new Bike();
            case TRUCK:
                return new Truck();
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle car = VehicleFactory.createVehicle(VehicleType.CAR);
        Vehicle bike = VehicleFactory.createVehicle(VehicleType.BIKE);
        Vehicle truck = VehicleFactory.createVehicle(VehicleType.TRUCK);

        System.out.println(car.getDetails());
        System.out.println(bike.getDetails());
        System.out.println(truck.getDetails());

        System.out.println("Car rent for 3 days: $" + car.calculateRent(3));
        System.out.println("Bike rent for 2 days: $" + bike.calculateRent(2));
        System.out.println("Truck rent for 5 days: $" + truck.calculateRent(5));
    }
}  