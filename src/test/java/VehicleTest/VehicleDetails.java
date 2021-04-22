package VehicleTest;

import ParkingApp.ParkingException.ParkingException;
import ParkingApp.Vehicles.Vehicle;
import ParkingApp.Vehicles.VehicleFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VehicleDetails {

    Vehicle vehicle;
    String vehicleLicensePlate;
    String vehicleType;
    long timeIn;

    @BeforeEach
    public void init() throws ParkingException
    {
        VehicleFactory vehicleFactory = new VehicleFactory();
        vehicleLicensePlate = "SX1234A";
        vehicleType = "car";
        timeIn = 123124l;
        vehicle = vehicleFactory.createVehicle("car", "SX1234A", 123124l);
    }

    @Test
    public void getVehicleDetails()
    {
        assertAll("vehicleDetails",
                () -> assertEquals(vehicleLicensePlate, vehicle.getLicensePlate()),
                () -> assertEquals(vehicleType, vehicle.getVehicleType()),
                () -> assertEquals(timeIn, vehicle.getTimeIn())
                );
    }
}
