package ParkingApp.Vehicles;

import ParkingApp.ParkingException.ParkingException;

/**
 * Factory class responsible for creating vehicles. All creation of vehicles through this class.
 */
public class VehicleFactory {

    public VehicleFactory()
    {

    }

    // Main creation method that calls corresponding vehicle constructors based on input types
    public Vehicle createVehicle(String vehicleType, String licensePlate, Long timeIn) throws ParkingException
    {
        if ( vehicleType.isEmpty() || licensePlate.isEmpty() || timeIn == null)
        {
            throw new ParkingException("Vehicle Exception: Failed to create vehicle, vehicle detail missing.");
        }

        switch(vehicleType)
        {
            case "car":
                return new Car(vehicleType, licensePlate, timeIn);

            case "motorcycle":
                return new MotorCycle(vehicleType, licensePlate, timeIn);

            default:
                throw new ParkingException("Vehicle Exception: Failed to create vehicle, invalid vehicle.");
        }
    }

}
