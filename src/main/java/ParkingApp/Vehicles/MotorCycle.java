package ParkingApp.Vehicles;

/**
 * A valid and concrete implementation of vehicle as defined by input.
 */
public class MotorCycle extends Vehicle {

    public MotorCycle(String vehicleType, String licensePlate, Long timeIn)
    {
        super(vehicleType, licensePlate, timeIn);
    }

    //TODO: possible specific implementations for motorcycle e.g. engine size
}
