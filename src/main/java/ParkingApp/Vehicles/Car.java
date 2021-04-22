package ParkingApp.Vehicles;

/**
 * A valid and concrete implementation of vehicle as defined by input.
 */
public class Car extends MotorCycle {

    public Car(String vehicleType, String licensePlate, Long timeIn)
    {
        super(vehicleType, licensePlate, timeIn);
    }

    //TODO: possible specific implementations for motorcycle e.g. seat number
}
